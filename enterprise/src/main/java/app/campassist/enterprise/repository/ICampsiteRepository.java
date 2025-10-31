package app.campassist.enterprise.repository;

/**
 * Repository interface for managing campsite data.
 *
 * <p>This interface defines the contract for data access operations related to
 * {@link app.campassist.enterprise.dto.CampsiteDTO} objects. Implementations
 * of this interface will handle persistence, retrieval, and query operations
 * for campsites in the underlying data store.</p>
 *
 * <p>Currently, this interface does not declare any methods, but it can be
 * extended in the future to include CRUD operations such as:</p>
 * <ul>
 *     <li>save(CampsiteDTO campsite)</li>
 *     <li>findById(UUID id)</li>
 *     <li>findAll()</li>
 *     <li>deleteById(UUID id)</li>
 * </ul>
 *
 * <p>In Spring Data JPA, this interface could extend {@code JpaRepository<CampsiteDTO, UUID>}
 * to automatically inherit standard CRUD operations.</p>
 */
public interface ICampsiteRepository {
    // Define data access methods here
}
