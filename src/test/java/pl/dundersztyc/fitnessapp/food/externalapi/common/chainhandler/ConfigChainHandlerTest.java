package pl.dundersztyc.fitnessapp.food.externalapi.common.chainhandler;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

class ConfigChainHandlerTest {

    @Test
    void shouldConfigChainHandler() {
        var handlers = prepareHandlersInOrder(3);
        var handler = ConfigChainHandler.configChainHandler(handlers);

        chainHandlerShouldBeInOrder(handler);
        chainHandlerShouldBeSize(handler, 3);
    }

    @Test
    void shouldDeleteNullHandlers() {
        LinkedHashSet<FakeChainHandler> handlers = new LinkedHashSet<>();
        handlers.add(new FakeChainHandler(1));
        handlers.add(null);
        handlers.add(null);
        handlers.add(new FakeChainHandler(2));

        var handler = ConfigChainHandler.configChainHandler(handlers);

        chainHandlerShouldBeInOrder(handler);
        chainHandlerShouldBeSize(handler, 2);
    }

    @Test
    void shouldThrowWhenHandlersAreNull() {
        assertThrows(NullPointerException.class, () -> {
            ConfigChainHandler.configChainHandler(null);
        });
    }


    private void chainHandlerShouldBeInOrder(FakeChainHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler is null");
        }
        int expectedHandlerId = 1;
        int expectedNextHandlerId = 2;

        while (handler.nextHandler != null) {
            assertThat(handler.id).isEqualTo(expectedHandlerId);
            assertThat(handler.nextHandler.id).isEqualTo(expectedNextHandlerId);

            ++expectedHandlerId;
            ++expectedNextHandlerId;

            handler = handler.nextHandler;
        }
    }

    private void chainHandlerShouldBeSize(FakeChainHandler handler, int expectedSize) {
        if (handler == null) {
            assertThat(0).isEqualTo(expectedSize);
            return;
        }
        int size = 1;
        while (handler.nextHandler != null) {
            ++size;
            handler = handler.nextHandler;
        }
        assertThat(size).isEqualTo(expectedSize);
    }

    private LinkedHashSet<FakeChainHandler> prepareHandlersInOrder(int numberOfHandlers) {
        LinkedHashSet<FakeChainHandler> handlers = new LinkedHashSet<>();
        for (int i = 1; i <= numberOfHandlers; ++i) {
            handlers.add(new FakeChainHandler(i));
        }
        return handlers;
    }


    @RequiredArgsConstructor
    private class FakeChainHandler implements ChainHandler<FakeChainHandler> {

        final int id;
        FakeChainHandler nextHandler;

        @Override
        public void setNextHandler(FakeChainHandler nextHandler) {
            this.nextHandler = nextHandler;
        }
    }

}