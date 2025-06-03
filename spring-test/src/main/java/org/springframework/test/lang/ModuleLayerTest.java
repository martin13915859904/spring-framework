package org.springframework.test.lang;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModuleLayerTest {
	public static void main(String[] args) {
		Set<String> systemModules =ModuleFinder.ofSystem().findAll().stream()
				.map(moduleReference -> moduleReference.descriptor().name())
				.collect(Collectors.toSet());
		System.out.println("systemModules---------->");
		systemModules.forEach(systemModule-> System.out.println(systemModule));
		System.out.println("bootModules?---------->");
		ModuleLayer.boot().configuration().modules().stream()
			.forEach(resolvedModule -> {
				// NOTE: a ModuleReader and a Stream returned from ModuleReader.list() must be closed.
				try (ModuleReader moduleReader = resolvedModule.reference().open();
					 Stream<String> names = moduleReader.list()) {
					names.forEach(name-> System.out.println(name));
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}
			});
	}
}
