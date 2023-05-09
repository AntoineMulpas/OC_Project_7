package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    public CurvePoint addACurvePoint(CurvePoint curvePoint) {
        LocalDateTime localDateTime = LocalDateTime.now();
        curvePoint.setCreationDate(Timestamp.valueOf(localDateTime));
        return curvePointRepository.save(curvePoint);
    }

    public List <CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    public boolean deleteCurvePointById(Integer id) {
        Optional <CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        if (optionalCurvePoint.isPresent()) {
            curvePointRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id).orElse(null);
    }

    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        Optional <CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        if (optionalCurvePoint.isPresent()) {
            optionalCurvePoint.get().setCurveId(curvePoint.getCurveId());
            optionalCurvePoint.get().setTerm(curvePoint.getTerm());
            optionalCurvePoint.get().setValue(curvePoint.getValue());
            return curvePointRepository.save(optionalCurvePoint.get());
        }
        return null;
    }

}
