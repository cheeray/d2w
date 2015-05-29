package com.d2w.core;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.persistence.Entity;

import com.d2w.annotation.Tow;
import com.d2w.exe.TowException;

/**
 * A WELD extension to find all annotated data path to be exposed.
 * 
 * @author Chengwei.Yan
 */
public class PathFinder implements Extension {

	/**
	 * Load path.
	 * 
	 * @throws IllegalAccessException
	 * @throws TowException
	 */
	<A> void processAnnotatedType(@Observes ProcessAnnotatedType<A> pat)
			throws IllegalAccessException, TowException {
		final MethodHandles.Lookup lookup = MethodHandles.lookup();
		final AnnotatedType<A> at = pat.getAnnotatedType();
		if (at.isAnnotationPresent(Tow.class)) {
			final Class<A> javaClass = at.getJavaClass();
			// Check package annotation ...
			if (javaClass.getSimpleName().endsWith("package-info")) {
				final ClassLoader classLoader = javaClass.getClassLoader();
				final Package pack = javaClass.getPackage();
				// Find enums ...
				findEnumsInPackage(pack, classLoader);
			} else {
				final Tow classTow = at.getAnnotation(Tow.class);
				String root = classTow.path();
				if (root.isEmpty()) {
					root = at.getJavaClass().getCanonicalName();
				}
				final boolean isEntity = at.isAnnotationPresent(Entity.class);

				// Map annotated method ...
				for (AnnotatedField<?> af : at.getFields()) {
					if (af.isAnnotationPresent(Tow.class)) {
						final Tow tow = af.getAnnotation(Tow.class);
						final Field f = af.getJavaMember();
						String path = tow.path();
						if (path.isEmpty()) {
							path = f.getName();
						}
						if (isEntity || Modifier.isStatic(f.getModifiers())) {
							TowMap.map(root, javaClass, path, tow.type(),
									lookup.unreflectGetter(f), isEntity);
						} else {
							throw new IllegalArgumentException(
									"Unable to map non static field: "
											+ f.getName()
											+ " for a non entity class:"
											+ javaClass);

						}
					}
				}
			}
		}
	}

	/**
	 * Find annotated enums in package.
	 * 
	 * @param pack
	 *            The package.
	 * @param classLoader
	 *            The class loader.
	 * @throws TowException
	 *             while failed to load an enum class.
	 */
	private static void findEnumsInPackage(Package pack, ClassLoader classLoader)
			throws TowException {
		final URL url = classLoader.getResource("/"
				+ pack.getName().replaceAll("\\.", "/"));
		final File p = new File(url.getPath());
		for (File f : p.listFiles()) {
			String name = f.getName();
			if (f.isFile() && name.endsWith(".class")) {
				name = pack.getName() + "." + name.replace(".class", "");
				try {
					final Class<?> clazz = Class.forName(name, true,
							classLoader);
					if (clazz.isEnum() && clazz.isAnnotationPresent(Tow.class)) {
						final Tow tow = clazz.getAnnotation(Tow.class);
						String root = tow.path();
						if (root.isEmpty()) {
							root = clazz.getCanonicalName();
						}
						for (Enum e : clazz.asSubclass(Enum.class)
								.getEnumConstants()) {
							TowMap.map(root, clazz.getClass(), e.name(),
									tow.type(), null, false);
						}
					}
				} catch (ClassNotFoundException e) {
					throw new TowException(e);
				}
			}
		}
	}
}
