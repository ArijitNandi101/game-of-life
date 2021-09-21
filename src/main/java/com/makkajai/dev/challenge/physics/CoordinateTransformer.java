package com.makkajai.dev.challenge.physics;

import com.makkajai.dev.challenge.physics.ITransformable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoordinateTransformer<T extends ITransformable<T>> {
    
    T offset;

    public T transform(T coordinate) {
        return coordinate.translate(offset);
    }
}
