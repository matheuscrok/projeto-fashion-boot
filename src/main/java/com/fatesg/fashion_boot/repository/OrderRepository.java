package com.fatesg.fashion_boot.repository;

import com.fatesg.fashion_boot.entity.Ordem;
import com.fatesg.fashion_boot.entity.dto.OrderByDayDTO;
import com.fatesg.fashion_boot.entity.dto.OrderByStatusDTO;
import com.fatesg.fashion_boot.entity.dto.SaleByMonthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Ordem, Long> {

    @Query("select u from Ordem u where u.usuario.name like %:#{[0]}% and u.usuario.name like %:name%")
    Page<Ordem> findByName(String name, Pageable pageable);

    List<Ordem> findAllByUsuarioSub(String sub);
 
    @Query( value =
            " select    "
            + "   case MONTH(a.date_purchase)    "
            + "     when 1 then 1    "
            + "     when 2 then 2    "
            + "     when 3 then 3    "
            + "     when 4 then 4    "
            + "     when 5 then 5    "
            + "     when 6 then 6    "
            + "     when 7 then 7    "
            + "     when 8 then 8    "
            + "     when 9 then 9    "
            + "     when 10 then 10    "
            + "     when 11 then 11    "
            + "     else 12     "
            + "   end monthNumber,    "
            + "   case MONTH(a.date_purchase)    "
            + "     when 1 then 'Janeiro'    "
            + "     when 2 then 'Fevereiro'    "
            + "     when 3 then 'Março'    "
            + "     when 4 then 'Abril'    "
            + "     when 5 then 'Maio'    "
            + "     when 6 then 'Junho'    "
            + "     when 7 then 'Julho'    "
            + "     when 8 then 'Agosto'    "
            + "     when 9 then 'Setembro'    "
            + "     when 10 then 'Outubro'    "
            + "     when 11 then 'Novembro'    "
            + "     else 'Desembro'     "
            + "   end monthDescription,    "
            + "     sum(b.quantity * c.sale_price) totalValue    "
            + " from ordem a     "
            + " inner join item_ordered b on a.id = b.ordem_id    "
            + " inner join product c on b.product_id = c.id    "
            + " where     "
            + "    a.status = 'Finalizado'    "
            + "      AND year(a.date_purchase) = year(now())    "
            + " group by     "
            + "   1,2    "
            + " order by 1    ", nativeQuery = true)
    List<SaleByMonthDTO> findSaleByMonth();

     @Query( value =
             " select "
             + "     sum(b.quantity * c.sale_price) quantity, "
             + "     a.status, "
             + " case a.status "
             + "   when 'Finalizado' then '#008a49' "
             + "   when 'Pendente' then '#b9bf00' "
             + "   when 'Cancelado' then '#ff7333' "
             + " end color "
             + " from ordem a  "
             + " inner join item_ordered b on a.id = b.ordem_id "
             + " inner join product c on b.product_id = c.id "
             + " where  "
             + "    month(a.date_purchase) = month(now()) "
             + " group by  "
             + "     a.status "
             + " order by "
                 + " case a.status  "
                 + "    when 'Finalizado' then 1  "
                 + "    when 'Pendente' then 2 "
                 + "    when 'Cancelado' then 3 "
                 + " end "
             , nativeQuery = true)
    List<OrderByStatusDTO> findOrderByStatus();

     @Query( value =
             " select  "
             + "   a.id orderNumber,  "
             + "     d.name userName,  "
             + "     d.phone,  "
             + "     sum(b.quantity * c.sale_price) totalValue,  "
             + "     a.status  "
             + " from ordem a   "
             + " inner join item_ordered b on a.id = b.ordem_id  "
             + " inner join product c on b.product_id = c.id  "
             + " inner join usuario d on a.usuario_id = d.id  "
             + " where   "
             + "    day(a.date_purchase) = day(now())  "
             + " group by   "
             + "   a.id,  "
             + "     d.name,  "
             + "     d.phone,  "
             + "     a.status  "
             + " order by a.id  ", nativeQuery = true)
    List<OrderByDayDTO> findOrderByDay();

}
