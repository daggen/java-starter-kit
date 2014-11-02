package se.daggen.common.converter;

public interface Converter<FROM, TO> {

	TO convert(FROM from);
}
