package com.cl1ppz12.projectzerolimit.util;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class DescriptionBuilder {
    private final List<Text> description = new ArrayList<>();

    public DescriptionBuilder addTitle(Text title, Formatting color, boolean showFlare){
        String unicode = showFlare ? "\ue001" : "\ue002";
        Text line = showFlare ? title.copy().append(" " + unicode).formatted(color)
                : title.copy().formatted(color);
        description.add(line);
        return this;
    }

    public DescriptionBuilder addDescription(Text desc){
        description.add(desc.copy().formatted(Formatting.GRAY));
        return this;
    }

    public DescriptionBuilder addDamage(float damage) {
        String formattedDamage = String.format("%.2f", damage);
        this.description.add(Text.translatable("project_zero_limit.ability_info.damage", formattedDamage, Formatting.RED));
        return this;
    }


    public DescriptionBuilder addCost(float cost) {
        this.description.add(Text.translatable("project_zero_limit.ability_info.cost", (int)cost, Formatting.GOLD));
        return this;
    }

    public DescriptionBuilder addEffect(String effectTitle, String effectdesc) {
        this.description.add(Text.translatable(effectTitle, Formatting.DARK_AQUA));
        this.description.add(Text.translatable(effectdesc, Formatting.GRAY));
        return this;
    }

    public DescriptionBuilder addCooldown(float cooldown) {
        this.description.add(Text.translatable("project_zero_limit.ability_info.cooldown", (int)cooldown + "s", Formatting.DARK_GRAY));
        return this;
    }

    public DescriptionBuilder addEmptyLine() {
        this.description.add(Text.empty());
        return this;
    }

    public List<Text> build() {
        return this.description;
    }
}