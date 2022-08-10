package br.com.elo7.starlink.infra.repository.position;

import br.com.elo7.starlink.domains.position.Position;
import br.com.elo7.starlink.domains.position.PositionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionRepositoryImpl implements PositionRepository {

    @Autowired
    private PositionRepositorySpring repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(Position position) {
        var entity = modelMapper.map(position, PositionEntity.class);
        repository.save(entity);
    }
}
