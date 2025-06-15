package com.example.backend.Service;
import java.util.List;

import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;

import com.example.backend.Data.TodoList;
import com.example.backend.api.response.SearchResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.Query;

@Service
public class TodoListServiceImpl implements  TodoListService{
    
    @PersistenceContext
    private EntityManager em;
  
    @Transactional(rollbackOn = { Exception.class})
    public SearchResult<TodoList> searchTodoListByTitlePaged(String title, int pageNumber, int rowPerPage) {
        int startRow = (pageNumber - 1) * rowPerPage;

        // สร้าง count SQL โดยเริ่มต้นมี WHERE 1=1 เพื่อให้ต่อ AND ง่าย
        String countSql = "SELECT COUNT(*) FROM TODO_LIST WHERE 1=1 ";
        String dataSql = "SELECT * FROM TODO_LIST WHERE 1=1 ";

        boolean hasTitle = title != null && !title.trim().isEmpty();

        if (hasTitle) {
            countSql += " AND title LIKE :title ";
            dataSql += " AND title LIKE :title ";
        }

        dataSql += " ORDER BY id DESC LIMIT :limit OFFSET :offset ";

        Query countQuery = em.createNativeQuery(countSql);
        Query dataQuery = em.createNativeQuery(dataSql, TodoList.class);

        if (hasTitle) {
            String likeTitle = "%" + title.trim() + "%";
            countQuery.setParameter("title", likeTitle);
            dataQuery.setParameter("title", likeTitle);
        }

        int totalRecords = ((Number) countQuery.getSingleResult()).intValue();
        int totalPages = (int) Math.ceil((double) totalRecords / rowPerPage);

        List<TodoList> resultList = dataQuery
            .setParameter("limit", rowPerPage)
            .setParameter("offset", startRow)
            .getResultList();

        SearchResult<TodoList> result = new SearchResult<>();
        result.setResultList(resultList);
        result.setTotalRecords(totalRecords);
        result.setRowPerPage(rowPerPage);
        result.setCurrentPage(pageNumber);
        result.setTotalPages(totalPages);

        return result;
    }

    public SearchResult<TodoList> getTodoListPaged(int pageNumber, int rowPerPage) {
    int startRow = (pageNumber - 1) * rowPerPage;

    // COUNT total
    String countSql = "SELECT COUNT(*) FROM TODO_LIST";
    int totalRecords = ((Number) em.createNativeQuery(countSql).getSingleResult()).intValue();
    int totalPages = (int) Math.ceil((double) totalRecords / rowPerPage);

    // SELECT page data
    String dataSql = "SELECT * FROM TODO_LIST ORDER BY id DESC LIMIT :limit OFFSET :offset";
    List<TodoList> resultList = em.createNativeQuery(dataSql, TodoList.class)
        .setParameter("limit", rowPerPage)
        .setParameter("offset", startRow)
        .getResultList();

    // Wrap response
    SearchResult<TodoList> result = new SearchResult<>();
    result.setResultList(resultList);
    result.setTotalRecords(totalRecords);
    result.setRowPerPage(rowPerPage);
    result.setCurrentPage(pageNumber);
    result.setTotalPages(totalPages);

    return result;
}

    @Transactional(rollbackOn = { Exception.class})
    public void insertTodoList(TodoList poTodoList) throws ApplicationContextException {
        try {
            // Validate: (ถ้ามี) เช่น ห้ามเพิ่มข้อมูลซ้ำ อาจตรวจสอบได้ตรงนี้

            // สร้าง SQL Insert Statement
            StringBuilder sqlStmt = new StringBuilder();
            sqlStmt.append(" INSERT INTO TODO_LIST ");
            sqlStmt.append(" (title, description, create_date, updated_at, status, priority, is_deleted)  ");
            sqlStmt.append(" VALUES (?, ?, NOW(), NOW(), ?, ?, ? ) ");

            int vnCnt = 1;
            Query voQuery = em.createNativeQuery(sqlStmt.toString());

            // Set Parameter ตามลำดับ
            voQuery.setParameter(vnCnt++, poTodoList.getTitle());
            voQuery.setParameter(vnCnt++, poTodoList.getDescription());
            voQuery.setParameter(vnCnt++, poTodoList.getStatus());
            voQuery.setParameter(vnCnt++, poTodoList.getPriority());
            voQuery.setParameter(vnCnt++, poTodoList.getIsDeleted());

            // Execute Insert
            voQuery.executeUpdate();

        } catch (ApplicationContextException e) {
            throw new ApplicationContextException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationContextException("TodoListService.insertTodoList : " + e.getMessage());
        }
    }

    @Transactional(rollbackOn = { Exception.class})
    public void updateTodoList(TodoList poTodoList) throws ApplicationContextException {
        try {
            // สร้าง SQL Update Statement
            StringBuilder sqlStmt = new StringBuilder();
            sqlStmt.append(" UPDATE TODO_LIST SET ");
            sqlStmt.append(" title = ?, ");
            sqlStmt.append(" description = ?, ");
            sqlStmt.append(" updated_at = NOW(), ");
            sqlStmt.append(" status = ?, ");
            sqlStmt.append(" priority = ?, ");
            sqlStmt.append(" is_deleted = ? ");
            sqlStmt.append(" WHERE id = ? ");

            int vnCnt = 1;
            Query voQuery = em.createNativeQuery(sqlStmt.toString());

            // Set Parameter ตามลำดับ
            voQuery.setParameter(vnCnt++, poTodoList.getTitle());
            voQuery.setParameter(vnCnt++, poTodoList.getDescription());
            voQuery.setParameter(vnCnt++, poTodoList.getStatus());
            voQuery.setParameter(vnCnt++, poTodoList.getPriority());
            voQuery.setParameter(vnCnt++, poTodoList.getIsDeleted());
            voQuery.setParameter(vnCnt++, poTodoList.getId()); // เงื่อนไข WHERE id = ?

            // Execute Update
            int updatedRows = voQuery.executeUpdate();

            if (updatedRows == 0) {
                throw new ApplicationContextException("No TodoList record found with id = " + poTodoList.getId());
            }

        } catch (ApplicationContextException e) {
            throw new ApplicationContextException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationContextException("TodoListService.updateTodoList : " + e.getMessage());
        }
    }

    public TodoList getTodoListById(int id) throws ApplicationContextException {
    try {
        String sql = "SELECT * FROM TODO_LIST WHERE id = ? ";

        Query query = em.createNativeQuery(sql, TodoList.class);
        query.setParameter(1, id);

        List<TodoList> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            throw new ApplicationContextException("ไม่พบข้อมูลที่ต้องการค้นหา");
        }

        return resultList.get(0);

        } catch (ApplicationContextException e) {
            throw new ApplicationContextException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationContextException("TodoListService.getTodoListById : " + e.getMessage());
        }
    }

    @Transactional(rollbackOn = { Exception.class })
    public void deleteTodoListById(int id) throws ApplicationContextException {
        try {
            String sql = "DELETE FROM TODO_LIST WHERE id = ?";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, id);

            int deletedRows = query.executeUpdate();

            if (deletedRows == 0) {
                throw new ApplicationContextException("ไม่พบข้อมูลที่ต้องการลบด้วย id = " + id);
            }

        } catch (ApplicationContextException e) {
            throw new ApplicationContextException(e.getMessage());
        } catch (Exception e) {
            throw new ApplicationContextException("TodoListService.deleteTodoListById : " + e.getMessage());
        }
    }

}
