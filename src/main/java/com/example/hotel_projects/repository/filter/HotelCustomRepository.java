package com.example.hotel_projects.repository.filter;

import com.example.hotel_projects.dto.page.PaginationResultDTO;
import com.example.hotel_projects.dto.request.HotelFilterDto;
import com.example.hotel_projects.entity.HotelEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PaginationResultDTO<HotelEntity> filter(HotelFilterDto filter, int page, int size) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (filter.getHotelId() != null) {
            builder.append("and id =:id ");
            params.put("id", filter.getHotelId());
        }
        if (filter.getHotelName() != null) {
            builder.append("and name =:name ");
            params.put("name", filter.getHotelName());
        }
       if(filter.getRegionId()!=null){
           builder.append("and region_id=:region_id ");
           params.put("region_id", filter.getRegionId());
       }
        if(filter.getNumberStars()!=null){
            builder.append("and number_starts=:number_starts ");
            params.put("number_starts", filter.getNumberStars());
        }


        StringBuilder selectBuilder = new StringBuilder("FROM HotelEntity s where 1=1 ");
        selectBuilder.append(builder);
        selectBuilder.append(" order by createdDate desc ");

        StringBuilder countBuilder = new StringBuilder("Select count(s) FROM HotelEntity s where 1=1 ");
        countBuilder.append(builder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size); // limit
        selectQuery.setFirstResult((page - 1) * size); // offset (page-1)*size

        Query countQuery = entityManager.createQuery(countBuilder.toString());


        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<HotelEntity> entityList = selectQuery.getResultList();
        Long totalElements = (Long) countQuery.getSingleResult();

        return new PaginationResultDTO<HotelEntity>(totalElements, entityList);
    }

}
