package ru.nsu.ccfit.tihomolov.digital_library.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.tihomolov.digital_library.models.dto.PublisherDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Publisher;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.AuthorNotFoundException;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.PublisherAlreadyExistException;
import ru.nsu.ccfit.tihomolov.digital_library.models.exception.PublisherNotFoundException;
import ru.nsu.ccfit.tihomolov.digital_library.models.mapper.MapperAuthor;
import ru.nsu.ccfit.tihomolov.digital_library.models.mapper.MapperPublisher;
import ru.nsu.ccfit.tihomolov.digital_library.repository.jpa.PublisherRepository;
import ru.nsu.ccfit.tihomolov.digital_library.util.ContextValidation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements LibraryService<PublisherDto> {
    private final PublisherRepository publisherRepository;

    private<T extends RuntimeException> Publisher findPublisher(String name, T publisherException) {
        return publisherRepository.findByTitle(name).orElseThrow(() -> publisherException);
    }

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void add(PublisherDto publisherDto) {
        publisherRepository.findByTitle(publisherDto.getTitle())
                .ifPresent((publisher) -> {
                    throw new PublisherAlreadyExistException(publisherDto.getTitle());
                });

        publisherRepository.save(MapperPublisher.mapperToEntity(publisherDto));
    }

    @Override
    public PublisherDto getInfoByTitle(String title) {
        return MapperPublisher.mapperToDto(findPublisher(title, new AuthorNotFoundException(title)));
    }

    @Override
    public void edit(String oldTitle, PublisherDto publisherDto) {
        Publisher publisher = findPublisher(oldTitle, new AuthorNotFoundException(oldTitle));
        publisher.setDescription(publisherDto.getDescription());
        publisher.setYear(publisherDto.getYear());
        publisher.setTitle(publisher.getTitle());
        publisherRepository.save(publisher);
    }

    @Override
    public void delete(String title) {
        Publisher publisher = findPublisher(title, new AuthorNotFoundException(title));
        publisherRepository.delete(publisher);
    }

    @Override
    public Set<PublisherDto> getPage(Integer page) {
        return publisherRepository.findAll(PageRequest.of(page, ContextValidation.PAGE_SIZE)).stream()
                .map(MapperPublisher::mapperToDto).collect(Collectors.toSet());
    }
}
