package com.epam.esm.service.impl;

import com.epam.esm.converter.CertificateDtoConverter;
import com.epam.esm.converter.TagDtoConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.impl.Certificate;
import com.epam.esm.entity.impl.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.impl.CertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static com.epam.esm.exception.message.ServiceExceptionMessage.ALREADY_EXISTS;
import static com.epam.esm.exception.message.ServiceExceptionMessage.NOT_FOUND;

/**
 * Class provides business logic methods for CRUD and search operations
 * concerning {@link Certificate} entity
 * @author Marianna Patrusova
 * @version 1.0
 */
@Service
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateDtoConverter certificateDtoConverter;
    private final TagRepository tagRepository;
    private final TagDtoConverter tagDtoConverter;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  CertificateDtoConverter certificateDtoConverter,
                                  TagRepository tagRepository,
                                  TagDtoConverter tagDtoConverter) {
        this.certificateRepository = certificateRepository;
        this.certificateDtoConverter = certificateDtoConverter;
        this.tagRepository = tagRepository;
        this.tagDtoConverter = tagDtoConverter;
    }

    /**
     * Method: find one {@link Certificate} entity
     * @param id: certificate id
     * @throws ServiceException if entity not found
     * @return instance of ({@link CertificateDto}
     */
    @Override
    public CertificateDto findById(long id) throws ServiceException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            return certificateDtoConverter.toResponseDto(certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    /**
     * Method: find one or more {@link Certificate} entity
     * @return list of ({@link CertificateDto} entities
     */
    @Override
    public List<CertificateDto> findAll() {
        final List<Certificate> certificates = certificateRepository.findAll();
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    /**
     * Method: save one {@link Certificate} entity and its tags
     * @throws ServiceException if entity already exists
     * @param certificateDto: instance of {@link CertificateDto}
     */
    @Override
    public void save(CertificateDto certificateDto) throws ServiceException {
        final Certificate certificate = certificateDtoConverter.toNewCertificate(certificateDto);
        Certificate savedCertificate;
        try {
            savedCertificate = certificateRepository.save(certificate);
        } catch (DaoException e) {
            throw new ServiceException(ALREADY_EXISTS, e);
        }
        updateTags(certificateDto, savedCertificate.getId());
        log.info("Certificate has been saved {}", savedCertificate);
    }

    /**
     * Method: update fields of one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param certificateDto: instance of {@link CertificateDto}
     */
    @Override
    public void update(CertificateDto certificateDto) throws ServiceException {
        final Certificate certificate = certificateDtoConverter.toUpdatedCertificate(certificateDto);
        Certificate updatedCertificate;
        try {
            updatedCertificate = certificateRepository.update(certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
        updateTags(certificateDto, updatedCertificate.getId());
        log.info("Certificate has been updated {}", updatedCertificate);
    }

    /**
     * Method: delete one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param id: certificate id
     */
    @Override
    public void delete(long id) throws ServiceException {
        try {
            certificateRepository.delete(id);
            log.info("Certificate has been deleted {}", id);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
    }

    private void updateTags(CertificateDto certificateDto, long id) {
        List<TagDto> allTags = certificateDto.getTags();
        List<Tag> newTags = saveTags(allTags);
        if (newTags.size() > 0) {
            saveCertificateCrossTag(id, newTags);
        }
        deleteCertificateCrossTag(id, allTags);
    }

    private List<Tag> saveTags(List<TagDto> tags) {
        return tags.stream()
                .filter(tag -> tag.getId() == null)
                .map(tag ->
                {
                    Tag newTag = tagDtoConverter.toNewTag(tag);
                    return tagRepository.save(newTag);
                })
                .collect(Collectors.toList());
    }

    private void deleteCertificateCrossTag(long id, List<TagDto> tags) {
        tags.stream()
                .filter(tag -> tag.getName().equals("deleted"))
                .forEach(tag -> certificateRepository.deleteCertificateCrossTag(id, tag.getId()));
    }

    private void saveCertificateCrossTag(long id, List<Tag> tags) {
        tags.forEach(tag -> certificateRepository.saveCertificateCrossTag(id, tag.getId()));
    }

}
