package nl.devpieter.falsereality.modifiers.abstraction;

public interface IModifier {

    String getName();

    boolean isEnabled();

    void setEnabled(boolean enabled);
}
