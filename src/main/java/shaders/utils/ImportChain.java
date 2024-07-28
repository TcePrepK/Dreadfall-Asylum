package shaders.utils;

import java.util.ArrayList;

public record ImportChain(String path, ArrayList<ImportChain> imports) {
}
