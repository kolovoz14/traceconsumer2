package es.upm.dit.apsv.traceconsumer2.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.apsv.traceconsumer2.model.Trace;

public interface TraceRepository extends CrudRepository<Trace, String> {
}
