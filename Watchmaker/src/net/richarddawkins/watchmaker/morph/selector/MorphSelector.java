package net.richarddawkins.watchmaker.morph.selector;

import java.util.Collection;

import net.richarddawkins.watchmaker.morph.Morph;

public interface MorphSelector {
    Morph best(Morph target, Collection<Morph> morphs);
}
