package particle_model;

import lib.data_structures.specialized_container.CollectionContainer;
import lib.debug.MethodNameHack;
import lib.java_api_extensions.MathX;
import lib.tokens.enums.CopyType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import static java.lang.Double.NaN;
import static lib.debug.AssertMessages.BAD_CODE_PATH;
import static lib.debug.Debug.assert_msg;
import static lib.java_api_extensions.MathX.max;

public class Universe<T extends Particle> implements CollectionContainer<T> {

    public double acceleration_constant;
    public double max_speed;

    public LinkedList<T> particles = new LinkedList<>();

    double max_starting_velocity;

    public Universe(double acceleration_constant, double max_speed, Iterable<T> particles, CopyType copy_type) {
        this.acceleration_constant = acceleration_constant;
        this.max_speed = max_speed;

        copy_particles(particles, copy_type);
        max_starting_velocity = max_velocity();
    }

    public Universe(double acceleration_constant, double max_speed, CopyType copy_type, T... particles) {
        this(acceleration_constant, max_speed, Arrays.asList(particles), copy_type);
    }

    public Universe(Universe<T> u, CopyType copy_type) {
        this(u.acceleration_constant, u.max_speed, u.particles, copy_type);
    }

    public Containable<T> new_copy(CopyType copy_type) {
        return new Universe<>(acceleration_constant, max_speed, particles, copy_type);
    }

    public void copy_particles(Iterable<T> particles, CopyType copy_type) {
        Iterator<T> p_iterator = particles.iterator();

        switch (copy_type) {
            case COPY_SHALLOW:
                this.particles = new LinkedList<>();
                while (p_iterator.hasNext())
                    this.particles.add(p_iterator.next());
                break;
            case COPY_DEEP:
                this.particles = new LinkedList<>();
                while (p_iterator.hasNext())
                    this.particles.add(p_iterator.next().new_copy());
                break;
        }
    }

    public Iterator<T> iterator() {
        return particles.iterator();
    }

    public double max_velocity() {
        try {
            return max(
                    (Object p) -> ((Particle) p).velocity(),
                    particles.iterator()
            );
        } catch (MathX.UndefinedMaxExc e) {
            assert false : assert_msg(
                    this.getClass(),
                    new MethodNameHack() {}.method_name(),
                    BAD_CODE_PATH);
            return NaN;
        }
    }
}
