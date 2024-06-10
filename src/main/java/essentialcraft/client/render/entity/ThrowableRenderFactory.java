package essentialcraft.client.render.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ThrowableRenderFactory<T extends Entity> implements IRenderFactory<T> {

	public Item itemToRender;

	public ThrowableRenderFactory(Item i) {
		itemToRender = i;
	}

	@Override
	public Render<? super T> createRenderFor(RenderManager manager) {
		return new RenderSnowball<>(manager, itemToRender, Minecraft.getMinecraft().getRenderItem());
	}
}
