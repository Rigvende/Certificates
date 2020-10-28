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
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
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
    public CertificateDto findById(Long id) throws ServiceException {
        try {
            Certificate certificate = certificateRepository.findEntityById(id);
            setTags(certificate);
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
        certificates.forEach(this::setTags);
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
        updateTagList(certificateDto.getTags(), savedCertificate.getId());
        log.info("Certificate has been saved {}", savedCertificate);
    }

    /**
     * Method: update fields of one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param certificateDto: instance of {@link CertificateDto}
     */
    @Override
    public void update(Long id, CertificateDto certificateDto) throws ServiceException {
        Certificate certificate = certificateRepository.findEntityById(id);
        certificate = certificateDtoConverter.toUpdatedCertificate(certificate, certificateDto);
        Certificate updatedCertificate;
        try {
            updatedCertificate = certificateRepository.update(certificate);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
        updateTagList(certificateDto.getTags(), id);
        log.info("Certificate has been updated {}", updatedCertificate);
    }

    /**
     * Method: delete one {@link Certificate} entity
     * @throws ServiceException if entity not found
     * @param id: certificate id
     */
    @Override
    public void delete(Long id) throws ServiceException {
        try {
            certificateRepository.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(NOT_FOUND, e);
        }
        log.info("Certificate has been deleted {}", id);
    }

    /**
     * Method: find all certificates by related tag name.
     * @param  tagName: tag name
     * @return list of {@link CertificateDto}
     */
    @Override
    public List<CertificateDto> findAllByTag(String tagName) {
        final List<Certificate> certificates = certificateRepository.findAllByTag(tagName);
        certificates.forEach(this::setTags);
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    /**
     * Method: find all certificates sorted by parameters
     * @param  direction: sort direction
     * @param column: sort column
     * @return list of {@link CertificateDto}
     */
    @Override
    public List<CertificateDto> findAllSorted(String direction, String column) {
        final List<Certificate> certificates = certificateRepository.findAllSorted(direction, column);
        certificates.forEach(this::setTags);
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    /**
     * Method: find all certificates by name
     * @param  name: certificate name or part of name
     * @return list of {@link CertificateDto}
     */
    @Override
    public List<CertificateDto> findAllByName(String name) {
        final List<Certificate> certificates = certificateRepository.findAllByName(name);
        certificates.forEach(this::setTags);
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    /**
     * Method: find all certificates by description
     * @param  description: certificate description or part of description
     * @return list of {@link CertificateDto}
     */
    @Override
    public List<CertificateDto> findAllByDescription(String description) {
        final List<Certificate> certificates = certificateRepository.findAllByDescription(description);
        certificates.forEach(this::setTags);
        return certificateDtoConverter.toResponseDtoList(certificates);
    }

    //method for tags manipulations during creating/modifying a certificate
    private void updateTagList(List<TagDto> tagDtoList, Long id) {
        //create link between certificate & old tag if link not exists
        List<Tag> unlinkedTags = findTagsWithoutLinks(tagDtoList);
        if (unlinkedTags != null && unlinkedTags.size() > 0) {
            saveCertificateTagLink(id, unlinkedTags);
        }
        //save new tags if not exist:
        List<Tag> newTags = saveNewUniqueTags(tagDtoList);
        //create links between certificate & new tags:
        if (newTags != null && newTags.size() > 0) {
            saveCertificateTagLink(id, newTags);
        }
        //delete link between certificate and non-implemented tag
        deleteCertificateTagLink(id, tagDtoList);
    }

    private List<Tag> saveNewUniqueTags(List<TagDto> tags) {
        if (tags != null) {
            return tags.stream()
                    .filter(tag -> tag.getStatus().equals(TagDto.TagStatus.NEW)
                            && tagRepository.findByName(tag.getName()).size() == 0)
                    .map(tag -> tagRepository.save(tagDtoConverter.toNewTag(tag)))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void saveCertificateTagLink(Long id, List<Tag> tags) {
        tags.forEach(tag -> certificateRepository.saveCertificateTagLink(id, tag.getId()));
    }

    private List<Tag> findTagsWithoutLinks(List<TagDto> tags) {
        if (tags != null) {
            return tags.stream()
                    .filter(tag -> (tag.getStatus().equals(TagDto.TagStatus.NEW))
                            && tagRepository.findByName(tag.getName()).size() > 0)
                    .map(tag -> tagRepository.findByName(tag.getName()).get(0))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void deleteCertificateTagLink(Long id, List<TagDto> tags) {
        if (tags != null) {
            tags.stream()
                    .filter(tag -> tag.getStatus().equals(TagDto.TagStatus.DELETED))
                    .forEach(tag -> certificateRepository.deleteCertificateTagLink(id, tag.getId()));
        }
    }

    private void setTags(Certificate certificate) {
        List<Tag> tags = tagRepository.findAllByCertificate(certificate.getId());
        certificate.setTagList(tags);
    }

}
