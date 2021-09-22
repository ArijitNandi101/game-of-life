package com.makkajai.dev.challenge.physics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoordinateTransformer<T extends ITransformable<T>> {
    
    T offset;

    public T transform(T coordinate) {
        return coordinate.translate(offset);
    }
}
