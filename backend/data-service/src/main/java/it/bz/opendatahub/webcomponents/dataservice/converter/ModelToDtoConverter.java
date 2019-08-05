package it.bz.opendatahub.webcomponents.dataservice.converter;

import it.bz.opendatahub.webcomponents.dataservice.data.Dto;
import it.bz.opendatahub.webcomponents.dataservice.data.Model;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ModelToDtoConverter<M extends Model, D extends Dto> {
    protected static final ModelMapper modelMapper = new ModelMapper();

    private final Type dtoType;

    public ModelToDtoConverter() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        @SuppressWarnings("unchecked")
        Class<D> ret = (Class<D>) parameterizedType.getActualTypeArguments()[1];

        dtoType = ret;
    }

    public D modelToDto(M model) {
        return modelMapper.map(model, dtoType);
    }

    public final List<D> modelToDto(List<M> models) {
        if(models == null) {
            return Collections.emptyList();
        }

        List<D> resList = new ArrayList<>();

        for(M model : models) {
            resList.add(modelToDto(model));
        }

        return resList;
    }
}
