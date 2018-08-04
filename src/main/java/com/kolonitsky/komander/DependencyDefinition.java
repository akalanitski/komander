package com.kolonitsky.komander;

/**
 * Definition of dependency for IOC
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 30.04.2018.
 */
public class DependencyDefinition {
	public String key;
	public Object instance;
	public Configurator configurator;
}
