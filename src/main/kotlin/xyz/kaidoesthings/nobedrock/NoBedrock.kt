package xyz.kaidoesthings.nobedrock

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Blocks
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class NoBedrock : ModInitializer {
    private val logger: Logger = LoggerFactory.getLogger("nobedrock")
    override fun onInitialize() {
        logger.info("No Bedrock mod initialized")
        UseBlockCallback.EVENT.register { plr, world, hand, result ->
            if(!world.isClient) {
                val blockPos = result.blockPos.offset(result.side)
                world.getBlockState(blockPos)?.let {
                    if(plr.getStackInHand(hand).item.equals(Blocks.BEDROCK.asItem())) {
                        logger.info("Prevented ${plr.name} from placing bedrock 😎")
                        plr.sendMessage(Text.literal("Right-click interactions are disabled when bedrock is equipped.").formatted(Formatting.RED, Formatting.BOLD), true)
                        return@register ActionResult.FAIL
                    }
                }
            }
            return@register ActionResult.PASS
        }
    }
}
