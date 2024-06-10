package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Coctel;
import com.example.demo.domain.TipoCoctel;
import com.example.demo.repository.CoctelRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CoctelServiceTest {
    ArrayList<Coctel> mockList;
    @InjectMocks
    CoctelServiceImpl coctelServiceImpl;

    @Mock
    CoctelRepository coctelRepository;

    @BeforeAll
    public void init() {
        mockList = new ArrayList<>();
        mockList.add(new Coctel(1L, "Mojito", TipoCoctel.Aperetivo, "ron, menta", 13.00f, 6, null));
        mockList.add(new Coctel(2L, "Martini", TipoCoctel.Aperetivo, "algo", 13.00f, 6, null));
    }

    @Test
    public void obtenerTodosTest() {
        when(coctelRepository.findAll()).thenReturn(mockList);
        List<Coctel> empList = coctelServiceImpl.obtenerTodos();
        assertEquals(2, empList.size());
        verify(coctelRepository, times(1)).findAll();
    }
}
