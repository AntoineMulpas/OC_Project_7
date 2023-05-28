package com.example.poseidoninc.services;

import com.example.poseidoninc.domain.CurvePoint;
import com.example.poseidoninc.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This Class is the Service layer for the Curve object.
 * It is annotated with the @Transaction to manage transactions of
 * all the methods contained in this class.
 */

@Service
@Transactional
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * This method is used to add a new Curve.
     * @param curvePoint
     * @return it returns the saved curve.
     */

    public CurvePoint addACurvePoint(CurvePoint curvePoint) {
        LocalDateTime localDateTime = LocalDateTime.now();
        curvePoint.setCreationDate(Timestamp.valueOf(localDateTime));
        return curvePointRepository.save(curvePoint);
    }

    /**
     * This method is used to retrive all curves.
     * @return List of all curves.
     */

    public List <CurvePoint> getAllCurvePoint() {
        return curvePointRepository.findAll();
    }

    /**
     * This method is used to delete a specific bid.
     * @param id
     * @return it returns a boolean depending on the outcome of the operation.
     */

    public boolean deleteCurvePointById(Integer id) {
        Optional <CurvePoint> optionalCurvePoint = curvePointRepository.findById(id);
        if (optionalCurvePoint.isPresent()) {
            curvePointRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * This method is used to get a specific curve depending on the id.
     * @param id
     * @return a specifc id found by id or null.
     */

    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id).orElse(null);
    }

    /**
     * This method is used to update a curve.
     * @param id
     * @param curvePoint
     * @return it returns the updated curve in case of success
     * or null if no curve for the specific id exists.
     */

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
