package com.makkajai.dev.challenge.physics;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A 2 dimensional coordinate transformer that transforms one {@link ITransformable}
 *  to another {@link ITransformable} after translating it by fixed offset in both axes.
 *
 * @param <T> a generic type that is also itself an {@link ITransformable}.
 */
@AllArgsConstructor
@Getter
public class CoordinateTransformer<T extends ITransformable<T>> {

    /**
     * The fixed offset value by which to translate other {@link ITransformable}s.
     */
    T offset;

    /**
     * Translates the transformable type using the {@link #offset} value and
     * returns a new transformable with the translated coordinates.
     * The original translatable is unaffected.
     *
     * @param coordinate the {@link ITransformable} specialized type to be translated.
     * @return a new intance of the {@link ITransformable} specialized type after translation.
     */
    public T transform(T coordinate) {
        return coordinate.translate(offset);
    }
}
