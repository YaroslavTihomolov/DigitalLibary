package ru.nsu.ccfit.tihomolov.digital_library.models.mapper;

import ru.nsu.ccfit.tihomolov.digital_library.models.dto.PublisherDto;
import ru.nsu.ccfit.tihomolov.digital_library.models.entity.jpa.Publisher;

public class MapperPublisher {
    public static PublisherDto mapperToDto(Publisher publisher) {
        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setDescription(publisher.getDescription());
        publisherDto.setYear(publisher.getYear());
        publisherDto.setTitle(publisher.getTitle());
        return publisherDto;
    }

    public static Publisher mapperToEntity(PublisherDto publisherDto) {
        Publisher publisher  = new Publisher();
        publisher.setDescription(publisherDto.getDescription());
        publisher.setYear(publisherDto.getYear());
        publisher.setTitle(publisherDto.getTitle());
        return publisher;
    }
}
