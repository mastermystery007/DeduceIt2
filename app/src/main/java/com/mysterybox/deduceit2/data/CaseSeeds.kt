package com.mysterybox.deduceit2.data

import com.mysterybox.deduceit2.data.Constraint.SuspectAt
import com.mysterybox.deduceit2.data.Constraint.SuspectHas
import com.mysterybox.deduceit2.data.Constraint.SuspectNotAt
import com.mysterybox.deduceit2.data.Constraint.SuspectNotHas
import com.mysterybox.deduceit2.data.Constraint.WeaponAt
import com.mysterybox.deduceit2.data.Constraint.WeaponNotAt

object CaseSeeds {
    val cases: List<MysteryCase> = listOf(
        MysteryCase(
            id = 1,
            title = "The Vanishing Violin",
            difficulty = "Easy",
            story = "A celebrated violinist disappeared minutes before a sold-out performance. Her broken instrument was found in the orchestra pit, and three people were seen moving through the theatre carrying objects that could have been used to restrain or threaten her. Determine who caused her disappearance, what they carried, and where they confronted her.",
            suspects = listOf("Mira Quill", "Owen Slate", "Priya Vale"),
            weapons = listOf("Rosin Bottle", "Tuning Fork", "Silk Cord"),
            locations = listOf("Backstage", "Green Room", "Orchestra Pit"),
            suspectDescriptions = mapOf(
                "Mira Quill" to "The soloist's ambitious understudy, calm in public but furious about being kept in reserve.",
                "Owen Slate" to "The theatre's exacting stage manager, responsible for keys, cues, and backstage access.",
                "Priya Vale" to "A former duet partner whose career collapsed after a bitter dispute with the missing violinist."
            ),
            weaponDescriptions = mapOf(
                "Rosin Bottle" to "A heavy glass bottle from the soloist's instrument case.",
                "Tuning Fork" to "A steel tuning fork engraved with the theatre crest.",
                "Silk Cord" to "A length of strong decorative cord cut from a stage curtain."
            ),
            locationDescriptions = mapOf(
                "Backstage" to "A narrow passage crowded with scenery and rigging.",
                "Green Room" to "The performers' lounge beside the dressing rooms.",
                "Orchestra Pit" to "The recessed performance area below the stage."
            ),
            clues = listOf(
                Clue("Mira Quill was in the Green Room.", SuspectAt("Mira Quill", "Green Room")),
                Clue("Owen Slate carried the Tuning Fork.", SuspectHas("Owen Slate", "Tuning Fork")),
                Clue("The Rosin Bottle was not in the Orchestra Pit.", WeaponNotAt("Rosin Bottle", "Orchestra Pit")),
                Clue("Owen Slate was not in the Orchestra Pit.", SuspectNotAt("Owen Slate", "Orchestra Pit"))
            ),
            solutionSuspect = "Priya Vale",
            solutionWeapon = "Silk Cord",
            solutionLocation = "Orchestra Pit",
            explanation = "Mira was in the Green Room. Owen had the Tuning Fork and was not in the pit, so he was Backstage. The Rosin Bottle could not be in the pit and therefore belonged to Mira. Priya was left in the Orchestra Pit with the Silk Cord, which matched fibres recovered from the broken violin case."
        ),
        MysteryCase(
            id = 2,
            title = "The Locked Greenhouse",
            difficulty = "Easy",
            story = "Botanist Dr. Rowan was found unconscious inside a greenhouse locked from the outside. A rare experimental compound was missing. Reconstruct the movements of the three people who remained on the estate and identify the attacker.",
            suspects = listOf("Asha Brant", "Julian Cross", "Nora Finch"),
            weapons = listOf("Pruning Shears", "Glass Vial", "Copper Wire"),
            locations = listOf("Greenhouse", "Boiler Room", "Potting Shed"),
            suspectDescriptions = mapOf(
                "Asha Brant" to "The estate engineer, familiar with the heating system and every service passage.",
                "Julian Cross" to "A pharmaceutical buyer who had repeatedly tried to purchase Rowan's research.",
                "Nora Finch" to "Rowan's research assistant, recently accused of falsifying laboratory notes."
            ),
            weaponDescriptions = mapOf(
                "Pruning Shears" to "Large shears carrying traces of wet soil.",
                "Glass Vial" to "A missing vial of a fast-acting botanical sedative.",
                "Copper Wire" to "Wire cut from the greenhouse's automatic lock controls."
            ),
            locationDescriptions = mapOf(
                "Greenhouse" to "The humid glass structure where Rowan was discovered.",
                "Boiler Room" to "The estate's hot, noisy maintenance room.",
                "Potting Shed" to "A cramped store of tools, soil, and seed trays."
            ),
            clues = listOf(
                Clue("Asha Brant was in the Boiler Room.", SuspectAt("Asha Brant", "Boiler Room")),
                Clue("Nora Finch carried the Copper Wire.", SuspectHas("Nora Finch", "Copper Wire")),
                Clue("The Glass Vial was in the Greenhouse.", WeaponAt("Glass Vial", "Greenhouse")),
                Clue("Nora Finch was not in the Greenhouse.", SuspectNotAt("Nora Finch", "Greenhouse"))
            ),
            solutionSuspect = "Julian Cross",
            solutionWeapon = "Glass Vial",
            solutionLocation = "Greenhouse",
            explanation = "Asha's Boiler Room alibi and Nora's possession of the Copper Wire remove both from the vial. Because the Glass Vial was in the Greenhouse and Nora was elsewhere, Julian was the person inside with the sedative."
        ),
        MysteryCase(
            id = 3,
            title = "Midnight at Platform Nine",
            difficulty = "Medium",
            story = "A night train was deliberately sent onto a closed line. The stationmaster was struck before he could reverse the signal. Three employees were present, each in a different station area and carrying a different piece of railway equipment.",
            suspects = listOf("Felix Ward", "Lena Moss", "Samir Holt"),
            weapons = listOf("Signal Key", "Brake Handle", "Lantern"),
            locations = listOf("Control Room", "Platform", "Baggage Hall"),
            suspectDescriptions = mapOf(
                "Felix Ward" to "The senior signal operator whose promotion had just been cancelled.",
                "Lena Moss" to "A conductor facing dismissal after repeated safety complaints.",
                "Samir Holt" to "A freight contractor paid to divert a valuable shipment."
            ),
            weaponDescriptions = mapOf(
                "Signal Key" to "The key that unlocks the manual signal controls.",
                "Brake Handle" to "A removable steel handle from an old carriage.",
                "Lantern" to "A heavy brass railway lantern with a cracked lens."
            ),
            locationDescriptions = mapOf(
                "Control Room" to "The locked room containing the signal levers.",
                "Platform" to "The empty passenger platform beside the closed line.",
                "Baggage Hall" to "A dark warehouse filled with freight cages."
            ),
            clues = listOf(
                Clue("The Brake Handle was on the Platform.", WeaponAt("Brake Handle", "Platform")),
                Clue("Felix Ward was not on the Platform.", SuspectNotAt("Felix Ward", "Platform")),
                Clue("Lena Moss was not in the Baggage Hall.", SuspectNotAt("Lena Moss", "Baggage Hall")),
                Clue("The Signal Key was not in the Baggage Hall.", WeaponNotAt("Signal Key", "Baggage Hall")),
                Clue("Samir Holt was not in the Control Room.", SuspectNotAt("Samir Holt", "Control Room")),
                Clue("Felix Ward carried the Signal Key.", SuspectHas("Felix Ward", "Signal Key"))
            ),
            solutionSuspect = "Samir Holt",
            solutionWeapon = "Lantern",
            solutionLocation = "Baggage Hall",
            explanation = "Felix had the Signal Key and could not be on the Platform, while the key was not in the Baggage Hall; he was therefore in the Control Room. Lena was not in the Baggage Hall, so she stood on the Platform with the Brake Handle. Samir remained in the Baggage Hall with the Lantern, whose cracked brass base matched the stationmaster's injury."
        ),
        MysteryCase(
            id = 4,
            title = "The Cipher Room",
            difficulty = "Medium",
            story = "A diplomatic cipher book vanished from a secure intelligence office. The archivist was found bound inside the Cipher Room. Three analysts had access to the floor, and each carried one object through one of three restricted rooms.",
            suspects = listOf("Iris Chen", "Noah Pike", "Elena March"),
            weapons = listOf("Brass Key", "Inked Glove", "Microfilm Reel"),
            locations = listOf("Archive", "Cipher Room", "Map Room"),
            suspectDescriptions = mapOf(
                "Iris Chen" to "The archive chief, protective of classified records and deeply suspicious of the new director.",
                "Noah Pike" to "A cryptographer secretly negotiating employment with a foreign contractor.",
                "Elena March" to "A cartographic analyst whose security clearance was about to be reduced."
            ),
            weaponDescriptions = mapOf(
                "Brass Key" to "An old master key retained for emergency access.",
                "Inked Glove" to "A leather glove stained with transfer ink from the stolen cipher book.",
                "Microfilm Reel" to "A reel containing photographs of restricted documents."
            ),
            locationDescriptions = mapOf(
                "Archive" to "A climate-controlled records vault.",
                "Cipher Room" to "The secure room where the archivist was discovered.",
                "Map Room" to "A wide room of charts, light tables, and locked drawers."
            ),
            clues = listOf(
                Clue("Iris Chen was not in the Cipher Room.", SuspectNotAt("Iris Chen", "Cipher Room")),
                Clue("The Microfilm Reel was in the Map Room.", WeaponAt("Microfilm Reel", "Map Room")),
                Clue("The Brass Key was in the Archive.", WeaponAt("Brass Key", "Archive")),
                Clue("Elena March was not in the Archive.", SuspectNotAt("Elena March", "Archive")),
                Clue("Noah Pike wore the Inked Glove.", SuspectHas("Noah Pike", "Inked Glove"))
            ),
            solutionSuspect = "Noah Pike",
            solutionWeapon = "Inked Glove",
            solutionLocation = "Cipher Room",
            explanation = "The Brass Key and Microfilm Reel place their holders in the Archive and Map Room. Noah had neither because he wore the Inked Glove. Iris could not be in the Cipher Room and Elena could not be in the Archive, forcing Iris into the Archive, Elena into the Map Room, and Noah into the Cipher Room."
        ),
        MysteryCase(
            id = 5,
            title = "The Gallery Alibi",
            difficulty = "Medium",
            story = "A collector collapsed during a private exhibition after discovering that one displayed painting was counterfeit. Exactly one of the three witnesses is lying. Use the physical evidence and their statements to identify the killer and the false witness.",
            suspects = listOf("Ravi Soren", "Claire Rowan", "Theo Wynn"),
            weapons = listOf("Marble Bust", "Palette Knife", "Solvent Flask"),
            locations = listOf("Sculpture Hall", "North Gallery", "Restoration Lab"),
            suspectDescriptions = mapOf(
                "Ravi Soren" to "A sculptor whose largest commission had been rejected by the victim.",
                "Claire Rowan" to "The gallery curator responsible for authenticating the disputed painting.",
                "Theo Wynn" to "A restoration specialist who had secretly altered the painting's provenance."
            ),
            weaponDescriptions = mapOf(
                "Marble Bust" to "A small but heavy study from the Sculpture Hall.",
                "Palette Knife" to "A sharpened restoration knife bearing fresh paint.",
                "Solvent Flask" to "A flask of toxic conservation solvent with its stopper removed."
            ),
            locationDescriptions = mapOf(
                "Sculpture Hall" to "A stone-floored hall of pedestals and statues.",
                "North Gallery" to "The exhibition room containing the counterfeit painting.",
                "Restoration Lab" to "A ventilated laboratory of pigments and solvents."
            ),
            clues = listOf(
                Clue("The Marble Bust was in the Sculpture Hall.", WeaponAt("Marble Bust", "Sculpture Hall")),
                Clue("Claire Rowan was in the North Gallery.", SuspectAt("Claire Rowan", "North Gallery"))
            ),
            statements = listOf(
                WitnessStatement("Ravi Soren", "Theo Wynn was in the Restoration Lab.", SuspectAt("Theo Wynn", "Restoration Lab")),
                WitnessStatement("Claire Rowan", "I carried the Solvent Flask.", SuspectHas("Claire Rowan", "Solvent Flask")),
                WitnessStatement("Theo Wynn", "Ravi Soren had the Marble Bust.", SuspectHas("Ravi Soren", "Marble Bust"))
            ),
            solutionSuspect = "Theo Wynn",
            solutionWeapon = "Solvent Flask",
            solutionLocation = "Restoration Lab",
            solutionLiar = "Claire Rowan",
            explanation = "Ravi's statement places Theo in the Restoration Lab, and Theo truthfully identifies Ravi with the Marble Bust in the Sculpture Hall. Claire is already fixed in the North Gallery, so her claim to have the Solvent Flask is the single lie. She had the Palette Knife, leaving Theo with the Solvent Flask in the lab."
        ),
        MysteryCase(
            id = 6,
            title = "The Observatory Blackout",
            difficulty = "Medium",
            story = "During a public comet viewing, the observatory lost power and its director was attacked. Three staff members were isolated in different parts of the building, each holding an object removed from its proper place.",
            suspects = listOf("Meera Knox", "Jon Bell", "Sofia Reed"),
            weapons = listOf("Star Chart Tube", "Heavy Battery", "Tripod Leg"),
            locations = listOf("Dome", "Generator Room", "Library"),
            suspectDescriptions = mapOf(
                "Meera Knox" to "An astronomer whose discovery credit had been reassigned to the director.",
                "Jon Bell" to "The maintenance chief who controlled the observatory's electrical system.",
                "Sofia Reed" to "A science journalist threatened with legal action over an unpublished investigation."
            ),
            weaponDescriptions = mapOf(
                "Star Chart Tube" to "A rigid metal tube used to store historic charts.",
                "Heavy Battery" to "A dense backup battery disconnected moments before the blackout.",
                "Tripod Leg" to "A detachable aluminium leg from the library's display telescope."
            ),
            locationDescriptions = mapOf(
                "Dome" to "The rotating telescope chamber beneath the open roof.",
                "Generator Room" to "The locked electrical room below the observatory.",
                "Library" to "A circular reading room lined with astronomical records."
            ),
            clues = listOf(
                Clue("The Heavy Battery was in the Generator Room.", WeaponAt("Heavy Battery", "Generator Room")),
                Clue("Sofia Reed was in the Library.", SuspectAt("Sofia Reed", "Library")),
                Clue("Meera Knox was not in the Generator Room.", SuspectNotAt("Meera Knox", "Generator Room")),
                Clue("The Tripod Leg was not in the Dome.", WeaponNotAt("Tripod Leg", "Dome"))
            ),
            solutionSuspect = "Jon Bell",
            solutionWeapon = "Heavy Battery",
            solutionLocation = "Generator Room",
            explanation = "Sofia's Library location and Meera's absence from the Generator Room leave Jon in the Generator Room with the Heavy Battery. The Tripod Leg could not be in the Dome, so Sofia had it and Meera had the Star Chart Tube. Jon used the battery to strike the director and then cut the power."
        ),
        MysteryCase(
            id = 7,
            title = "The Silent Auction",
            difficulty = "Hard",
            story = "A sealed-bid auction ended with the auctioneer dead and the winning ledger missing. The building was locked before police arrived. Determine how the three remaining guests were distributed across the venue and identify the killer.",
            suspects = listOf("Darius Vale", "Emi Laurent", "Kian Mercer"),
            weapons = listOf("Ivory Gavel", "Letter Opener", "Velvet Cord"),
            locations = listOf("Auction Hall", "Vault", "Loading Bay"),
            suspectDescriptions = mapOf(
                "Darius Vale" to "A rival auctioneer convinced the victim had rigged several major sales.",
                "Emi Laurent" to "An art dealer whose anonymous bids would have exposed illegal purchases.",
                "Kian Mercer" to "The venue owner facing bankruptcy if the ledger reached investigators."
            ),
            weaponDescriptions = mapOf(
                "Ivory Gavel" to "The ceremonial gavel used to close the final lot.",
                "Letter Opener" to "A narrow steel opener normally kept with the vault paperwork.",
                "Velvet Cord" to "A strong barrier cord removed from the loading entrance."
            ),
            locationDescriptions = mapOf(
                "Auction Hall" to "The main hall of numbered seats and display lights.",
                "Vault" to "A secure room containing documents and unsold valuables.",
                "Loading Bay" to "A concrete service area behind the venue."
            ),
            clues = listOf(
                Clue("The Ivory Gavel was not in the Vault.", WeaponNotAt("Ivory Gavel", "Vault")),
                Clue("The Letter Opener was in the Vault.", WeaponAt("Letter Opener", "Vault")),
                Clue("Darius Vale was not in the Loading Bay.", SuspectNotAt("Darius Vale", "Loading Bay")),
                Clue("Emi Laurent was not in the Auction Hall.", SuspectNotAt("Emi Laurent", "Auction Hall")),
                Clue("The Velvet Cord was not in the Auction Hall.", WeaponNotAt("Velvet Cord", "Auction Hall")),
                Clue("Darius Vale was in the Auction Hall.", SuspectAt("Darius Vale", "Auction Hall")),
                Clue("Kian Mercer was not in the Vault.", SuspectNotAt("Kian Mercer", "Vault"))
            ),
            solutionSuspect = "Kian Mercer",
            solutionWeapon = "Velvet Cord",
            solutionLocation = "Loading Bay",
            explanation = "Darius was in the Auction Hall, and the Velvet Cord was not there, so he had the Ivory Gavel. Kian was not in the Vault, forcing Emi into the Vault with the Letter Opener. Kian remained in the Loading Bay with the Velvet Cord, fibres from which were found on the auctioneer's collar."
        ),
        MysteryCase(
            id = 8,
            title = "The Snowbound Lodge",
            difficulty = "Hard",
            story = "An avalanche isolated a mountain lodge after its owner was killed. Exactly one guest is lying. The equipment room, great room, and pantry were searched before anything could be moved.",
            suspects = listOf("Anika Frost", "Bennett Cole", "Celia Hart"),
            weapons = listOf("Ice Axe", "Fireplace Poker", "Climbing Rope"),
            locations = listOf("Equipment Room", "Great Room", "Pantry"),
            suspectDescriptions = mapOf(
                "Anika Frost" to "A climbing guide blamed for an accident the lodge owner concealed.",
                "Bennett Cole" to "The owner's business partner, facing ruin after a secret audit.",
                "Celia Hart" to "A travel writer who had discovered the lodge was built on protected land."
            ),
            weaponDescriptions = mapOf(
                "Ice Axe" to "A mountaineering axe returned wet to the equipment rack.",
                "Fireplace Poker" to "A cast-iron poker missing from beside the main fireplace.",
                "Climbing Rope" to "A coil of rope cut from the emergency supplies."
            ),
            locationDescriptions = mapOf(
                "Equipment Room" to "A cold room of skis, ropes, and climbing tools.",
                "Great Room" to "The lodge's central room and the scene of the attack.",
                "Pantry" to "A narrow food store behind the kitchen."
            ),
            clues = listOf(
                Clue("The Ice Axe was in the Equipment Room.", WeaponAt("Ice Axe", "Equipment Room")),
                Clue("The Climbing Rope was in the Pantry.", WeaponAt("Climbing Rope", "Pantry"))
            ),
            statements = listOf(
                WitnessStatement("Anika Frost", "Bennett Cole was in the Great Room.", SuspectAt("Bennett Cole", "Great Room")),
                WitnessStatement("Bennett Cole", "Celia Hart carried the Fireplace Poker.", SuspectHas("Celia Hart", "Fireplace Poker")),
                WitnessStatement("Celia Hart", "Anika Frost had the Ice Axe.", SuspectHas("Anika Frost", "Ice Axe"))
            ),
            solutionSuspect = "Bennett Cole",
            solutionWeapon = "Fireplace Poker",
            solutionLocation = "Great Room",
            solutionLiar = "Bennett Cole",
            explanation = "The Ice Axe and Climbing Rope fix their holders in the Equipment Room and Pantry. Celia truthfully places the Ice Axe with Anika, and Anika truthfully places Bennett in the Great Room. Bennett's claim that Celia had the Poker is false: Celia had the Rope, leaving Bennett with the Fireplace Poker."
        ),
        MysteryCase(
            id = 9,
            title = "The Clockmaker's Last Hour",
            difficulty = "Hard",
            story = "A master clockmaker died precisely as every clock in his shop stopped. One prototype mechanism was sabotaged, and three associates were found in separate rooms carrying components removed from different clocks.",
            suspects = listOf("Leo Grant", "Maya Rook", "Omar Venn"),
            weapons = listOf("Winding Key", "Steel Spring", "Clock Weight"),
            locations = listOf("Workshop", "Showroom", "Office"),
            suspectDescriptions = mapOf(
                "Leo Grant" to "The clockmaker's apprentice, recently removed from the inventor's will.",
                "Maya Rook" to "A collector who had paid for a prototype that was never delivered.",
                "Omar Venn" to "A competitor accused of copying the clockmaker's patented escapement."
            ),
            weaponDescriptions = mapOf(
                "Winding Key" to "An oversized brass key from the sabotaged prototype.",
                "Steel Spring" to "A sharpened mainspring removed from a display clock.",
                "Clock Weight" to "A dense iron weight from the office regulator clock."
            ),
            locationDescriptions = mapOf(
                "Workshop" to "The crowded bench room where the prototype stood.",
                "Showroom" to "A polished gallery of ticking display clocks.",
                "Office" to "The clockmaker's private office behind the workshop."
            ),
            clues = listOf(
                Clue("The Winding Key was in the Workshop.", WeaponAt("Winding Key", "Workshop")),
                Clue("Maya Rook was not in the Office.", SuspectNotAt("Maya Rook", "Office")),
                Clue("Omar Venn was not in the Showroom.", SuspectNotAt("Omar Venn", "Showroom")),
                Clue("The Steel Spring was not in the Office.", WeaponNotAt("Steel Spring", "Office")),
                Clue("Leo Grant was not in the Showroom.", SuspectNotAt("Leo Grant", "Showroom")),
                Clue("Leo Grant carried the Winding Key.", SuspectHas("Leo Grant", "Winding Key"))
            ),
            solutionSuspect = "Omar Venn",
            solutionWeapon = "Clock Weight",
            solutionLocation = "Office",
            explanation = "Leo's Winding Key puts him in the Workshop. Maya was not in the Office and Leo was not in the Showroom, so Maya occupied the Showroom and Omar the Office. The Steel Spring was not in the Office, leaving it with Maya and the Clock Weight with Omar."
        ),
        MysteryCase(
            id = 10,
            title = "The Submarine Signal",
            difficulty = "Hard",
            story = "A research submarine surfaced after transmitting a false distress signal. Its captain was found unconscious, and a classified data drive was missing. Exactly one crew member is lying about the final minutes before the signal.",
            suspects = listOf("Nadia Shore", "Eli Mercer", "Tomas Reed"),
            weapons = listOf("Flare Gun", "Diving Knife", "Cable Loop"),
            locations = listOf("Bridge", "Engine Room", "Radio Room"),
            suspectDescriptions = mapOf(
                "Nadia Shore" to "The first officer, passed over when the captain extended his command.",
                "Eli Mercer" to "The chief engineer facing investigation over falsified maintenance reports.",
                "Tomas Reed" to "The communications specialist suspected of selling expedition data."
            ),
            weaponDescriptions = mapOf(
                "Flare Gun" to "The emergency flare pistol stored beside the bridge hatch.",
                "Diving Knife" to "A serrated knife from the engine-room dive locker.",
                "Cable Loop" to "A severed communications cable tied into a tight loop."
            ),
            locationDescriptions = mapOf(
                "Bridge" to "The command compartment containing navigation controls.",
                "Engine Room" to "The loud machinery compartment at the stern.",
                "Radio Room" to "The communications compartment where the false signal originated."
            ),
            clues = listOf(
                Clue("The Flare Gun was on the Bridge.", WeaponAt("Flare Gun", "Bridge")),
                Clue("The Diving Knife was in the Engine Room.", WeaponAt("Diving Knife", "Engine Room"))
            ),
            statements = listOf(
                WitnessStatement("Nadia Shore", "Eli Mercer was in the Engine Room.", SuspectAt("Eli Mercer", "Engine Room")),
                WitnessStatement("Eli Mercer", "Tomas Reed carried the Diving Knife.", SuspectHas("Tomas Reed", "Diving Knife")),
                WitnessStatement("Tomas Reed", "Nadia Shore had the Flare Gun.", SuspectHas("Nadia Shore", "Flare Gun"))
            ),
            solutionSuspect = "Tomas Reed",
            solutionWeapon = "Cable Loop",
            solutionLocation = "Radio Room",
            solutionLiar = "Eli Mercer",
            explanation = "Tomas truthfully places the Flare Gun with Nadia on the Bridge, and Nadia truthfully places Eli in the Engine Room with the Diving Knife. Eli's claim that Tomas had that knife is therefore the single lie. Tomas was in the Radio Room with the Cable Loop and used it before sending the false signal."
        )
    )
}
