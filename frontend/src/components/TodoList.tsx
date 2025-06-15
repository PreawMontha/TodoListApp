import React, { useState } from "react";
import EditIcon from "@mui/icons-material/Edit";
import {
  List,
  ListItem,
  Typography,
  Box,
  Button,
  Chip,
  IconButton,
} from "@mui/material";
import AlertDialog from "./AlertDialog";
import DeleteIcon from '@mui/icons-material/Delete';
import { CardListProps, Card } from "../types";

const TodoList: React.FC<CardListProps> = ({ cards, onEdit, onDelete }) => {
  
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedCardId, setSelectedCardId] = useState<number | null>(null);

  const mapStatus = (status: any): Card["status"] => {
  switch (String(status)) {
    case "1":
    case "Active":
      return "Active";
    case "0":
    case "Inactive":
      return "Inactive";
    default:
      return "Unknown";
  }
};

const mapPriority = (priority: any): Card["priority"] => {
  switch (Number(priority)) {
    case 1:
      return "Low";
    case 2:
      return "Medium";
    case 3:
      return "High";
    default:
      return "Low"; 
  }
};

  return (
   <>
  <AlertDialog
    open={openDialog}
     title="Confirm Deletion"
      message="Are you sure you want to delete this item?"
      onConfirm={() => {
        if (selectedCardId !== null) {
          onDelete(selectedCardId);
          setOpenDialog(false);
          setSelectedCardId(null);
        }
      }}
    onCancel={() => {
      setOpenDialog(false);
      setSelectedCardId(null);
    }}
  />

  <List sx={{ mt: 2, mx: 2 }}>
    {cards.length === 0 ? (
      <Typography
        sx={{
          textAlign: "center",
          mt: 4,
          color: "text.secondary",
          fontStyle: "italic",
        }}
      >
        Add your first task to get started!
        
      </Typography>
    ) : (
      cards
        .filter((card) => card !== undefined && card !== null)
        .map(({ id, title, priority, priorityDisplay, status, description }) => {
          const displayPriority = mapPriority(priority);
          const displayStatus = mapStatus(status);
          return (
            <ListItem
              key={id}
              sx={{
                mb: 2,
                borderRadius: 1,
                boxShadow: 1,
                bgcolor: "background.paper",
                display: "flex",
                flexDirection: { xs: "column", sm: "row" }, 
                alignItems: { xs: "flex-start", sm: "center" },
                justifyContent: "space-between",
                px: 2,
                py: 1.5,
                gap: 1,
              }}
            >
              <Box
                sx={{
                  display: "flex",
                  alignItems: "center",
                  width: { xs: "100%", sm: 200 },
                  mb: { xs: 1, sm: 0 },
                  overflow: "hidden",
                }}
              >
                <Typography fontWeight="bold" noWrap>
                  {title}
                </Typography>
              </Box>

              <Chip
                label={displayStatus}
                color={displayStatus === "Active" ? "success" : "error"}
                size="small"
                sx={{
                  minWidth: 80,
                  textAlign: "center",
                  mb: { xs: 1, sm: 0 },
                }}
              />

              <Box
                sx={{
                  display: "flex",
                  alignItems: "center",
                  ml: { xs: 0, sm: 2 },
                  minWidth: 100,
                  mb: { xs: 1, sm: 0 },
                }}
              >
                <Box
                  sx={{
                    width: 10,
                    height: 10,
                    borderRadius: "50%",
                    mr: 1,
                    bgcolor:
                      displayPriority === "High"
                        ? "error.main"
                        : displayPriority === "Medium"
                        ? "warning.main"
                        : "info.main",
                  }}
                />
                <Typography
                  sx={{
                    fontSize: "0.75rem",
                    fontWeight: 400,
                    color: "text.primary",
                  }}
                >
                  {displayPriority}
                </Typography>
              </Box>

              <Box
                sx={{
                  display: "flex",
                  alignItems: "center",
                  width: { xs: "100%", sm: 100 },
                  justifyContent: { xs: "flex-start", sm: "flex-end" },
                  gap: 1,
                  mt: { xs: 1, sm: 0 },
                }}
              >
                <Button
                  onClick={() => {
                    if (onEdit) {
                      onEdit({
                        id,
                        title,
                        description: description ?? undefined,
                        priority: priority,
                        status: displayStatus as Card["status"],
                      });
                    }
                  }}
                  size="small"
                  sx={{ minWidth: 0, p: 0.5 }}
                >
                  <EditIcon fontSize="small" />
                </Button>
                <IconButton
                  onClick={() => {
                    setSelectedCardId(id);
                    setOpenDialog(true);
                  }}
                  size="small"
                  sx={{ p: 0.5 }}
                >
                  <DeleteIcon fontSize="small" color="error" />
                </IconButton>
              </Box>
            </ListItem>
          );
        })
    )}
  </List>
</>

  );
};

export default TodoList;