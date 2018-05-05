package particle_model;

import lib.java_lang_extensions.parametrized_types.ConstructableBase;
import lib.data_structures.container.ContainerCopyableData;
import lib.tokens.enums.CopyType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import static lib.java_api_extensions.MathX.max;

public class Universe
        extends ContainerCopyableData<RenderableParticle, LinkedList<RenderableParticle>>
        implements ConstructableBase<Universe>
{
    public double acceleration_constant;
    public double max_velocity;

    public double max_starting_velocity;

    public Universe(double acceleration_constant, double max_velocity, Iterator<RenderableParticle> particles, CopyType copy_type) {
        this.contents = new LinkedList<>();
        this.acceleration_constant = acceleration_constant;
        this.max_velocity = max_velocity;

        add_data(particles, copy_type);
        max_starting_velocity = max_velocity();
    }

    public Universe(double acceleration_constant, double max_velocity, CopyType copy_type, RenderableParticle... particles) {
        this(acceleration_constant, max_velocity, Arrays.asList(particles).iterator(), copy_type);
    }

    public Universe(Universe u, CopyType copy_type) {
        this(u.acceleration_constant, u.max_velocity, u.contents.iterator(), copy_type);
    }

    public Universe new_copy(CopyType copy_type) {
        return new Universe(acceleration_constant, max_velocity, contents.iterator(), copy_type);
    }

    public Iterator<RenderableParticle> iterator() {
        return contents.iterator();
    }

    public double max_velocity() {
        return max(
                (RenderableParticle p) -> p.velocity(),
                contents.iterator()
        );
    }
}
