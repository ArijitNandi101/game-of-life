package com.makkajai.dev.challenge.physics;

/**
 * Represents the behaviors of an object that is transformable in space and time.
 *
 * @param <T>
 */
public interface ITransformable<T> {

    /**
     * The implementing class has to provide a means of translating
     * (moving alone a direction) itself.
     * @param rhs an instance of a transformable type that will describe the
     *            translation to perform on the implementing instance.
     * @return  a correctly translated instance
     */
    T translate(T rhs);

}
