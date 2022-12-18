package es.upm.dit.apsv.traceconsumer2.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.apsv.traceconsumer2.model.TransportationOrder;

public interface TransportationOrderRepository extends CrudRepository<TransportationOrder,String> {
    TransportationOrder findByTruckAndSt(String truck, int st);
}
