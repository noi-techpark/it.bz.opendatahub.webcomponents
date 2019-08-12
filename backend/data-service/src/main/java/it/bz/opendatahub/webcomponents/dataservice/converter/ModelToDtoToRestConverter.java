package it.bz.opendatahub.webcomponents.dataservice.converter;

import it.bz.opendatahub.webcomponents.common.data.Rest;
import it.bz.opendatahub.webcomponents.dataservice.data.Dto;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ModelToDtoToRestConverter<M, D extends Dto, R extends Rest> extends ModelToDtoConverter<M, D> {
    private final Type restType;

    public ModelToDtoToRestConverter() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        @SuppressWarnings("unchecked")
        Class<R> ret = (Class<R>) parameterizedType.getActualTypeArguments()[2];

        restType = ret;
    }

    public R dtoToRest(D dto) {
        return modelMapper.map(dto, restType);
    }

    public final List<R> dtoToRest(List<D> dtos) {
        if(dtos == null) {
            return Collections.emptyList();
        }

        List<R> resList = new ArrayList<>();

        for(D dto : dtos) {
            resList.add(dtoToRest(dto));
        }

        return resList;
    }

    public final R modelToRest(M model) {
        return dtoToRest(modelToDto(model));
    }

    public final List<R> modelToRest(List<M> models) {
        return dtoToRest(modelToDto(models));
    }
}
