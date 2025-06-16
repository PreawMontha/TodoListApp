import React, { useEffect, useState } from 'react';
import SlideBar from '../components/SlideBar';
import TopBar from '../components/TopBar';
import AddModal from '../components/Modal'; 
import TodoList from "../components/TodoList"; 
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import { todoListInstance } from '../service/todoInstance';
import AddIcon from '@mui/icons-material/Add';
import { Button } from '@mui/material';
import { Card } from "../types";

const Home = () => {

  const [cards, setCards] = useState<Card[]>([]);
  const [open, setOpen] = useState(false);

  const [formData, setFormData] = useState<{
    id: number;
    title: string;
    description?: string;
    status: number;
    priority: number;
  } | null>(null);

  const [pagination, setPagination] = useState({
    currentPage: 1,
    totalPages: 1,
    rowPerPage: 10,
    totalRecords: 0,
  });

  const handleEdit = (card: Card) => {
    setFormData({
      id: card.id,
      title: card.title,
      description: card.description,
      status: card.status === "Active" ? 1 : 0,
      priority: Number(card.priority),
    });
    setOpen(true);
  };


  // ฟังก์ชันดึงข้อมูลจาก API พร้อม paging
  const fetchCards = async (page: number = 1) => {
    try {
      const response = await todoListInstance.get('/getTodoList', {
       params: { pageNumber: page }
      });
      const data = response.data.data;
      setCards(data.resultList);

      setPagination({
        currentPage: data.currentPage,
        totalPages: data.totalPages,
        rowPerPage: data.rowPerPage,
        totalRecords: data.totalRecords,
      });

    } catch (error) {
      console.error("Failed to fetch cards", error);
    }
  };

  // โหลดข้อมูลครั้งแรกตอน component mount
  useEffect(() => {
    fetchCards();
  }, []);

  // ฟังก์ชันเพิ่มรายการใหม่
  const insertTodoList = async (data: { title: string; detail: string; status: number, priority: number }) => {
    try {
      const request = {
        title: data.title,
        description: data.detail,
        status: data.status,
        priority: data.priority,
        isDeleted: false,
      };

      const response = await todoListInstance.post("/insertTodoList", request);

      if (response.status === 200 && response.data.status === "SUCCESS") {
        setOpen(false);
        await fetchCards(); // ดึงข้อมูลใหม่
      } else {
        console.warn("Insert Failed:", response.data.message);
      }
    } catch (error) {
      console.error("Error inserting todo:", error);
    }
  };

  // ฟังก์ชันแก้ไขรายการ
  const updateTodoList = async (id: number, data: { title: string; detail: string; status: number; priority: number }) => {
    try {
      const request = {
        id,
        title: data.title,
        description: data.detail,
        status: data.status,
        priority: data.priority,
        isDeleted: false,
      };

      const response = await todoListInstance.post("/updateTodoList", request);

      if (response.status === 200 && response.data.status === "SUCCESS") {
        setOpen(false);
        await fetchCards();
      } else {
        console.warn("Update Failed:", response.data.message);
      }
    } catch (error) {
      console.error("Error updating todo:", error);
    }
  };

  const deleteTodoList = async (id: number) => {
    try {
      const response = await todoListInstance.post(`/deleteTodoList/${id}`);
      if (response.status === 200 && response.data.status === "SUCCESS") {
        await fetchCards();
      } else {
        console.warn("Delete Failed:", response.data.message);
      }
    } catch (error) {
      console.error("Error deleting todo:", error);
    }
  };

  const handleDeleteDialog = async (id: number) => {
    await deleteTodoList(id); 
  };

  const handleSubmit = async (data: { title: string; detail: string; status: number; priority: number }) => {
    if (formData) {
      await updateTodoList(formData.id, data); 
    } else {
      await insertTodoList(data);
    }
  };

  // เปลี่ยน priority ใน state 
  const onPriorityChange = (id: number, newPriority: Card["priority"]) => {
    setCards((prev) =>
      prev.map((card) =>
        card && card.id === id ? { ...card, priority: newPriority } : card
      )
    );
  };

  // เปลี่ยนหน้าของ pagination
  const handlePageChange = (event: React.ChangeEvent<unknown>, page: number) => {
    fetchCards(page);
  };

  // วันเวลาปัจจุบันรูปแบบแสดงผล
  const today = new Date();
  const formattedDate = today.toLocaleDateString("en-US", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  });

 return (
  <div className="h-screen w-screen bg-[#F8F8FF]">
    <div className="flex flex-col md:flex-row h-full">
      
      {/* Sidebar */}
      <div className="md:w-auto w-full">
        <SlideBar />
      </div>

      {/* Main content */}
      <div className="flex-1 flex flex-col">
        <div>
          <TopBar />
        </div>

        {/* Header area */}
        <div
          className="
              pl-4 pr-4 md:pl-10 md:pr-10 h-[100px] p-4 font-semibold text-gray-800 
              flex flex-col sm:flex-row justify-between items-start sm:items-center space-y-2 sm:space-y-0 sm:space-x-4"
            >
            <div>
              <div className="text-sm md:text-base">Today is,</div>
              <div className="text-2xl md:text-3xl">{formattedDate}</div>
            </div>

            <Button
              variant="contained"
              color="primary"
              size="medium"
              startIcon={<AddIcon />}
              sx={{
                borderRadius: 100,
                boxShadow: 3,
                textTransform: "none",
                fontWeight: "bold",
                px: 3,
                py: 1,
                backgroundColor: "#1976d2",
                "&:hover": {
                  backgroundColor: "#115293",
                },
                width: { xs: "100%", sm: "auto" }, // sm ขึ้นไปไม่เต็มความกว้าง
              }}
              onClick={() => setOpen(true)}
            >
              Add
            </Button>
              <AddModal
                open={open}
                onClose={() => {
                  setOpen(false);
                  setFormData(null);
                }}
                onSubmit={handleSubmit}
                formData={formData}
              />
            </div>

            {/* List area */}
            <div className="flex-1 overflow-y-auto px-4 md:px-10">
              <TodoList
                cards={cards}
                onPriorityChange={onPriorityChange}
                onEdit={handleEdit}
                onDelete={handleDeleteDialog}
              />
            </div>

            {/* Pagination */}
            {pagination.totalRecords > 0 && (
              <div className="bg-white py-4 px-4 mt-auto shadow-inner">
                <Stack spacing={2} direction="row" justifyContent="center">
                  <Pagination
                    count={pagination.totalPages}
                    page={pagination.currentPage}
                    onChange={handlePageChange}
                  />
                </Stack>
              </div>
            )}
          </div>
    </div>
  </div>
);
};

export default Home;
