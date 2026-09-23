# Dusk Horror (Forge, Minecraft 1.20.1)

A horror mod: a mob that only moves when you're not looking at it, a fear
meter that quietly ramps up ambient dread, jump scares, and a corrupted
dimension called **the Hollow**.

## What's in it

- **The Stalker** — a hostile mob that freezes solid the instant any player
  looks at it, and closes the distance the moment nobody is watching.
  Killing it drops **Corrupted Shards**.
- **Fear meter** — a hidden 0-100 value that rises in darkness, at night,
  near a Stalker, and especially inside the Hollow. It drives everything
  below and shows as a small bar above your hotbar.
- **Ambient horror** — random whispers, distant screams, and heartbeat
  sounds that get more frequent as fear rises.
- **Jump scares** — rare, fear-scaled stinger sound + white screen flash.
- **The Hollow** — a permanently dark, fixed-night, fog-choked dimension
  with a higher Stalker spawn rate.
- **Whispering Amulet** — right-click to teleport between the Overworld and
  the Hollow (no portal needed). Craft: 4 Corrupted Shards in a plus/cross
  shape around 1 Ender Pearl in the center of the crafting grid.
- **Warding Lantern** — hold it in either hand and the Stalker will never
  target you, and fear rises much more slowly. Craft: 8 Corrupted Shards
  surrounding a Lantern.

Everything is wired together and should compile as-is against Forge 1.20.1.

## Important — read this before building

I built this without a live compiler or the actual Minecraft/Forge
libraries in front of me (this sandbox has no internet access, so I
couldn't download them to test-compile). I'm confident in the overall
structure and most of the API calls, but two areas are the most likely
to need a small fix if Forge complains:

1. **`data/duskhorror/worldgen/biome/the_hollow.json` and the
   `dimension`/`dimension_type` JSON** — Minecraft's world-generation
   schema is intricate and shifts between versions.
2. **`TheHollowEffects.java`** — the exact `DimensionSpecialEffects`
   constructor signature.

If `./gradlew build` throws an error, **paste the error back to me and
I'll patch the exact file** — that's normal for modding iteration even
with a real compiler in front of you.

You'll also need to supply your own audio: `sounds.json` already points
at the right paths, but the actual `.ogg` files under
`assets/duskhorror/sounds/` don't exist yet (I can't generate audio).
Missing sound files just log a warning, they won't break the build.
Add files at:
```
assets/duskhorror/sounds/stalker/ambient.ogg
assets/duskhorror/sounds/stalker/scare.ogg
assets/duskhorror/sounds/ambient/whisper1.ogg
assets/duskhorror/sounds/ambient/whisper2.ogg
assets/duskhorror/sounds/ambient/scream.ogg
assets/duskhorror/sounds/ambient/heartbeat.ogg
```
The entity/item textures are simple placeholders I generated (a dark
silhouette, a shard, an eye amulet, a lantern) — swap them for real art
whenever you like at the same paths under `assets/duskhorror/textures/`.

## How to actually get a .jar

Compiling a real Forge mod requires Forge's own build tooling (Gradle +
ForgeGradle + the exact Minecraft/Forge libraries), downloaded from
Forge's servers. Two ways to do that — pick whichever is easier for you.

### Option A: Let GitHub build it for you (no local install at all)

This repo already includes `.github/workflows/build.yml`. It downloads
the real, official Forge 1.20.1 MDK (build 47.4.10) fresh from Forge's
server, drops this repo's `src/` into it, and runs the real build —
entirely on GitHub's servers, so you don't need Java, Gradle, or
anything else installed locally.

1. Create a new **empty** repository at github.com (no README/license,
   so it's truly empty).
2. On the repo page, click **Add file → Upload files**, then drag in
   this project's `src/` folder and `.github/` folder (and `README.md`)
   from your unzipped download — keep the folder structure as-is.
   Commit the upload.
3. Go to the **Actions** tab. A workflow run should already be
   underway (or click **Run workflow** if it isn't).
4. When it finishes (green check), open the run, scroll to
   **Artifacts**, and download **duskhorror-jar** — that's a zip
   containing the actual compiled `.jar`.
5. Drop that `.jar` into your Forge 1.20.1 `mods` folder.

No git command line needed — just drag-and-drop on GitHub's website.

### Option B: Build it yourself locally

1. Go to **https://files.minecraftforge.net/**, select Minecraft
   **1.20.1**, and download the **Mdk (Mod Development Kit)** zip for
   Forge **47.4.10** (current recommended build).
2. Unzip it somewhere.
3. Delete the MDK's example `src/main/java/...` and
   `src/main/resources/...` folders, and copy this project's `src/`
   folder in their place.
4. Open the MDK's `gradle.properties` and add/set:
   ```
   mod_id=duskhorror
   mod_name=Dusk Horror
   mod_version=1.0.0
   mod_group_id=com.duskhorror
   ```
5. From that folder, run:
   - macOS/Linux: `./gradlew build`
   - Windows: `gradlew.bat build`
6. Your jar appears at `build/libs/duskhorror-1.0.0.jar`. Drop it in
   your `mods` folder alongside Forge 1.20.1.

Both options end with the exact same kind of file: a standard Forge
mod jar that works dropped into any Forge 1.20.1 `mods` folder
(CurseForge-managed instance included), or uploaded to curseforge.com.

## Testing it

- Spawn one directly to test without waiting on natural spawn rules:
  `/summon duskhorror:stalker`
- Give yourself the amulet: `/give @s duskhorror:whispering_amulet`
- Give yourself the lantern: `/give @s duskhorror:warding_lantern`
- Right-click the amulet to jump straight to the Hollow.

## Ideas for "way more" later

- A placeable light-emitting lantern block instead of just a held item
- A proper obsidian-style portal frame instead of the amulet
- Additional hostile variants, or a boss version of the Stalker
- Structure generation (a ruined shrine) inside the Hollow

Happy to build out any of these next, or debug the first build with you.
