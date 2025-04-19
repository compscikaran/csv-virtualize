package dev.engineeringmadness.hephaestus.core.domain;

import dev.engineeringmadness.hephaestus.core.domain.AbstractQuery;

public interface QueryPlugin {
    AbstractQuery execute(AbstractQuery query);
}
