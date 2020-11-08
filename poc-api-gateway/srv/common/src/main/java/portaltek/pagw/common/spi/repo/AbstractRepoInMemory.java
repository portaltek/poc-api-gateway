package portaltek.pagw.common.spi.repo;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public abstract class AbstractRepoInMemory<T, ID> {
   private ConcurrentHashMap<ID, T> map = new ConcurrentHashMap<>();

   abstract protected ID getId(T entity);

   public Optional<T> findById(ID id) {
      return Optional.ofNullable(map.get(id));
   }

//    public Page<T> findAll(Pageable pageable) {
//        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
//    }

   public List<T> findBy(Predicate<T> predicate) {
      return map.values().stream()
         .filter(predicate)
         .collect(Collectors.toList());
   }

   public T save(T entity) {
      requireNonNull(entity);
      map.put(getId(entity), entity);
      return entity;
   }


}
