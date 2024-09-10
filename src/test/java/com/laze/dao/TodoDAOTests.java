package com.laze.dao;


import com.laze.w2.dao.TodoDAO;
import com.laze.w2.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {

    private TodoDAO todoDAO;

    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception {
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testInsert() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("sample title")
                .dueDate(LocalDate.of(2024,9,6))
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testSelectAll() throws Exception {

        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception {

        TodoVO vo = todoDAO.selectOne(1L);

        System.out.println(vo);

    }

    @Test
    public void testUpdate() throws Exception {
        TodoVO vo = TodoVO.builder()
                .tno(1L)
                .title("update title")
                .dueDate(LocalDate.of(2000,1,1))
                .finished(true)
                .build();

        todoDAO.updateOne(vo);
    }

    @Test
    public void testDelete() throws Exception {
        Long tno = 1L;

        todoDAO.deleteOne(tno);
    }
}
