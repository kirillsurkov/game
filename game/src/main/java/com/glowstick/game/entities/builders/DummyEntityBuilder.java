package com.glowstick.game.entities.builders;

import com.glowstick.engine.builders.entity.NamedEntityBuilder;
import com.glowstick.engine.caches.ModelCache;
import com.glowstick.engine.caches.ShaderCache;
import com.glowstick.game.entities.DummyEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class DummyEntityBuilder extends NamedEntityBuilder<DummyEntity> {
    private final String name = "dummy";

    @Override
    public DummyEntity build(ModelCache modelCache, ShaderCache shaderCache) throws Exception {
        return new DummyEntity(modelCache, shaderCache);
    }
}