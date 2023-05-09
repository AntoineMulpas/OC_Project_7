package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    private CurvePointService underTest;

    @Mock
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    void setUp() {
        underTest = new CurvePointService(curvePointRepository);
    }

    @Test
    void addACurvePoint() {
        CurvePoint curvePoint = new CurvePoint(
                1,
                10,
                Timestamp.valueOf(LocalDateTime.now()),
                10.4,
                10.5,
                Timestamp.valueOf(LocalDateTime.now()));

        when(curvePointRepository.save(any())).thenReturn(curvePoint);
        assertEquals(curvePoint, underTest.addACurvePoint(curvePoint));
    }

    @Test
    void getAllCurvePoint() {
        when(curvePointRepository.findAll()).thenReturn(List.of(new CurvePoint(), new CurvePoint()));
        assertEquals(2, underTest.getAllCurvePoint().size());
    }

    @Test
    void deleteCurvePointById() {
        when(curvePointRepository.findById(any())).thenReturn(Optional.of(new CurvePoint()));
        assertTrue(underTest.deleteCurvePointById(any()));
    }

    @Test
    void deleteCurvePointByIdShouldReturnFalse() {
        when(curvePointRepository.findById(any())).thenReturn(Optional.empty());
        assertFalse(underTest.deleteCurvePointById(any()));
    }

    @Test
    void findById() {
        when(curvePointRepository.findById(any())).thenReturn(Optional.of(new CurvePoint()));
        assertNotNull(underTest.findById(any()));
    }

    @Test
    void updateCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(
                1,
                10,
                Timestamp.valueOf(LocalDateTime.now()),
                10.4,
                10.5,
                Timestamp.valueOf(LocalDateTime.now()));

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
        assertEquals(10, underTest.updateCurvePoint(1, curvePoint).getCurveId());
    }

    @Test
    void updateCurvePointShouldReturnNull() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(underTest.updateCurvePoint(1, new CurvePoint()));
    }

}