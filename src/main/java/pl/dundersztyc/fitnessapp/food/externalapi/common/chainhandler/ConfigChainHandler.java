package pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class ConfigChainHandler {

    public static <T extends ChainHandler<T>> T configChainHandler(@NonNull LinkedHashSet<T> handlers) {
        List<T> handlerList = new ArrayList<>(handlers);
        handlerList.removeIf(Objects::isNull);
        for (int i = 0; i < handlerList.size() - 1; ++i) {
            handlerList.get(i).setNextHandler(handlerList.get(i + 1));
        }
        return handlerList.get(0);
    }

}