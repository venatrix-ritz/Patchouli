package vazkii.patchouli.client.multiblock;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;

// Code adapted from EnderIO
public final class MultiblockPiPRenderer extends PictureInPictureRenderer<MultiblockPiPRenderState> {

	@Override
	public Class<MultiblockPiPRenderState> getRenderStateClass() {
		return MultiblockPiPRenderState.class;
	}

	@Override
	protected void renderToTexture(MultiblockPiPRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector) {
		poseStack.pushPose();
		poseStack.mulPose(renderState.viewMatrix());
		Minecraft.getInstance().gameRenderer.lighting().setupFor(Lighting.Entry.ITEMS_3D);
		for (var block : renderState.multiblock()) {
			poseStack.pushPose();
			poseStack.translate(block.pos().getX() + 0.5F, block.pos().getY() + 0.5F, block.pos().getZ() + 0.5F);
			block.blockModelRenderState().submit(poseStack, collector, LightCoordsUtil.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0);
			poseStack.popPose();
		}
		poseStack.popPose();
	}

	@Override
	protected String getTextureLabel() {
		return "patchouli_multiblock";
	}
}
