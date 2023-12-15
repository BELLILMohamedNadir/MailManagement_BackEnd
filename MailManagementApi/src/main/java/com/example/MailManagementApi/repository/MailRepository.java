package com.example.MailManagementApi.repository;

import com.example.MailManagementApi.model.Mail;
        import jakarta.persistence.Tuple;
        import jakarta.transaction.Transactional;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;
        import java.util.Optional;

@Repository
@Transactional
public interface MailRepository extends JpaRepository<Mail,Long> {
    String Q=" SELECT m.id, a.archive, m.classed, m.departure_date, m.entry_date, f.favorite,\n" +
            "                m.internal_reference,m.received_category,m.entry_date_received,m.mail_reference,m.object_received, m.structure_id, m.type, m.mail_date,\n" +
            "                m.object, m.priority, m.recipient, m.response, m.response_of, t.trait,\n" +
            "                m.for_structure, c.cpt, c.designation as category,c.type, path.path, path.name,path.latest,s.designation_struct as sender" +
            "                ,f.initialized as initialized_favorite,a.initialized as initialized_archive,t.initialized as initialized_trait " +
            "                FROM Mail m\n" +
            "                LEFT JOIN Category c ON m.category_id = c.id\n" +
            "                RIGHT JOIN (\n" +
            "                SELECT latest,mail_id, GROUP_CONCAT(path SEPARATOR ',') AS path, GROUP_CONCAT(name SEPARATOR ',') AS name\n" +
            "                FROM path\n" +
            "                GROUP BY mail_id,latest\n" +
            "                ) path ON m.id = path.mail_id  " +
            "                LEFT JOIN STRUCTURE s ON m.structure_id=s.id" +
            "                LEFT JOIN FAVORITE f ON m.id=f.mail_id AND f.user_id=:userId " +
            "                LEFT JOIN ARCHIVE a ON m.id=a.mail_id AND a.user_id=:userId " +
            "                LEFT JOIN TRAIT t ON m.id=t.mail_id AND t.user_id=:userId " +
            "                WHERE path.latest=true ";


    String SEND_MAILS =Q+" AND m.structure_id =:id AND (a.archive is null OR a.archive=false) ";
    String FAVORITE_MAILS =Q+" AND m.structure_id =:id  AND f.favorite is not null AND f.favorite=true ";
    String ARCHIVE_MAILS =Q+" AND ( m.structure_id =:id OR m.for_structure=:reference )  AND a.archive is not null AND a.archive=true ";
    String ALL_MAILS =Q+" AND ( m.structure_id =:id OR (m.for_structure=:reference && m.received_category is not null)) AND (a.archive is null OR a.archive=false) ";
    String RECEIVED_MAILS =Q+" AND m.for_structure=:reference AND (a.archive is null OR a.archive=false) ";
    String TRAIT_MAILS =RECEIVED_MAILS+" AND t.trait is not null AND t.trait=true ";
    String TO_TRAIT_MAILS =Q+" AND m.for_structure=:reference AND (t.trait is null OR t.trait=false) ";
    String REFERENCES=" SELECT internal_reference FROM Mail ";

    @Query(value = SEND_MAILS,nativeQuery = true)
    List<Tuple> findSendMails(@Param("id") long id,@Param("userId") long userId);

    @Query(value = TRAIT_MAILS,nativeQuery = true)
    Optional<List<Tuple>> findTraitMails(@Param("reference") String reference,@Param("userId") long userId);

    @Query(value = TO_TRAIT_MAILS,nativeQuery = true)
    Optional<List<Tuple>> findToTraitMails(@Param("reference") String reference,@Param("userId") long userId);

    @Query(value = FAVORITE_MAILS,nativeQuery = true)
    List<Tuple> findFavoriteMails(@Param("id") long id,@Param("userId") long userId);

    @Query(value = ARCHIVE_MAILS,nativeQuery = true)
    List<Tuple> findArchiveMails(@Param("id") long id,@Param("userId") long userId,@Param("reference") String reference);

    @Query(value = ALL_MAILS,nativeQuery = true)
    List<Tuple> findAllMails(@Param("id") long id,@Param("userId") long userId,@Param("reference") String reference);

    @Query(value = REFERENCES,nativeQuery = true)
    List<String> getReferences(@Param("id") long id);

    @Query(value = RECEIVED_MAILS,nativeQuery = true)
    List<Tuple> findReceivedMails(@Param("reference") String reference,@Param("userId") long userId);

}
