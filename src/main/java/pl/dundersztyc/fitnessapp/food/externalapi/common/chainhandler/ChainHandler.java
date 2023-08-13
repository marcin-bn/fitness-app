package pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler;

public interface ChainHandler<T> {
    void setNextHandler(T nextHandler);
}
