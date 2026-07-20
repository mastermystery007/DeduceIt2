package com.mysterybox.deduceit2.data

import com.mysterybox.deduceit2.data.Constraint.SuspectAt
import com.mysterybox.deduceit2.data.Constraint.SuspectHas
import com.mysterybox.deduceit2.data.Constraint.SuspectNotAt
import com.mysterybox.deduceit2.data.Constraint.WeaponAt
import com.mysterybox.deduceit2.data.Constraint.WeaponNotAt

object CaseSeeds {
    val cases: List<MysteryCase> = listOf(
        MysteryCase(
            id = 1,
            title = "The Painted Cat",
            difficulty = "Easy",
            story = "The theatre's resident cat, Maestro, appeared before opening night covered in washable blue stage paint and wearing a glittery bow. Three people had been moving through the theatre with different craft items. Work out who gave Maestro the surprise makeover, what they carried, and where it happened.",
            suspects = listOf("Mira Quill", "Owen Slate", "Priya Vale"),
            weapons = listOf("Glitter Jar", "Feather Brush", "Blue Paint Tin"),
            locations = listOf("Backstage", "Green Room", "Orchestra Pit"),
            suspectDescriptions = mapOf(
                "Mira Quill" to "The soloist's imaginative understudy, known for decorating anything left unattended.",
                "Owen Slate" to "The theatre's exacting stage manager, responsible for props, cues, and backstage access.",
                "Priya Vale" to "A former duet partner visiting the theatre with a reputation for elaborate practical jokes."
            ),
            weaponDescriptions = mapOf(
                "Glitter Jar" to "A sealed jar of silver stage glitter from the costume cupboard.",
                "Feather Brush" to "A broad, soft brush normally used to dust velvet scenery.",
                "Blue Paint Tin" to "A small tin of washable blue stage paint with a loose lid."
            ),
            locationDescriptions = mapOf(
                "Backstage" to "A narrow passage crowded with scenery, costume racks, and prop tables.",
                "Green Room" to "The performers' lounge beside the dressing rooms.",
                "Orchestra Pit" to "The recessed performance area below the stage, where blue pawprints were noticed."
            ),
            clues = listOf(
                Clue("Mira Quill was in the Green Room.", SuspectAt("Mira Quill", "Green Room")),
                Clue("Owen Slate carried the Feather Brush.", SuspectHas("Owen Slate", "Feather Brush")),
                Clue("The Glitter Jar was not in the Orchestra Pit.", WeaponNotAt("Glitter Jar", "Orchestra Pit")),
                Clue("Owen Slate was not in the Orchestra Pit.", SuspectNotAt("Owen Slate", "Orchestra Pit"))
            ),
            solutionSuspect = "Priya Vale",
            solutionWeapon = "Blue Paint Tin",
            solutionLocation = "Orchestra Pit",
            explanation = "Mira was in the Green Room. Owen had the Feather Brush and was not in the Orchestra Pit, so he was Backstage. The Glitter Jar could not be in the pit and therefore belonged to Mira. Priya was left in the Orchestra Pit with the Blue Paint Tin, matching the washable paint on Maestro's fur and the pawprints below the stage."
        ),
        MysteryCase(
            id = 2,
            title = "The Mixed-Up Greenhouse",
            difficulty = "Easy",
            story = "Minutes before the estate's flower show, every rare plant label was switched and the prize orchids were arranged under the wrong names. Three people had been working in different areas with different gardening supplies. Determine who reorganized the display, what they used, and where they did it.",
            suspects = listOf("Asha Brant", "Julian Cross", "Nora Finch"),
            weapons = listOf("Watering Can", "Label Maker", "Copper Plant Tags"),
            locations = listOf("Greenhouse", "Boiler Room", "Potting Shed"),
            suspectDescriptions = mapOf(
                "Asha Brant" to "The estate engineer, familiar with the heating system and every service passage.",
                "Julian Cross" to "A competitive garden-show judge who enjoys testing whether exhibitors truly know their plants.",
                "Nora Finch" to "The botanist's research assistant, responsible for cataloguing seeds and updating plant records."
            ),
            weaponDescriptions = mapOf(
                "Watering Can" to "A green metal can still beaded with water from the morning round.",
                "Label Maker" to "A handheld printer loaded with the same white tape used on the altered labels.",
                "Copper Plant Tags" to "A bundle of reusable copper tags from the potting bench."
            ),
            locationDescriptions = mapOf(
                "Greenhouse" to "The humid glass structure containing the rearranged flower-show display.",
                "Boiler Room" to "The estate's hot, noisy maintenance room.",
                "Potting Shed" to "A cramped store of tools, soil, labels, and seed trays."
            ),
            clues = listOf(
                Clue("Asha Brant was in the Boiler Room.", SuspectAt("Asha Brant", "Boiler Room")),
                Clue("Nora Finch carried the Copper Plant Tags.", SuspectHas("Nora Finch", "Copper Plant Tags")),
                Clue("The Label Maker was in the Greenhouse.", WeaponAt("Label Maker", "Greenhouse")),
                Clue("Nora Finch was not in the Greenhouse.", SuspectNotAt("Nora Finch", "Greenhouse"))
            ),
            solutionSuspect = "Julian Cross",
            solutionWeapon = "Label Maker",
            solutionLocation = "Greenhouse",
            explanation = "Asha's Boiler Room location and Nora's Copper Plant Tags remove both from the Label Maker. Because the Label Maker was in the Greenhouse and Nora was elsewhere, Julian was the person inside printing the playful replacement labels."
        ),
        MysteryCase(
            id = 3,
            title = "The Scrambled Puzzle Board",
            difficulty = "Medium",
            story = "For the station's charity puzzle night, volunteers built a mechanical departure-board game in the Baggage Hall. Just before opening, its tiles were rearranged to show nonsense destinations such as Teapot Junction and Biscuit Bay. Three employees were in different station areas with different event supplies. Identify who altered the puzzle board, what they used, and where.",
            suspects = listOf("Felix Ward", "Lena Moss", "Samir Holt"),
            weapons = listOf("Cabinet Key", "Chalk Marker", "Timetable Roll"),
            locations = listOf("Control Room", "Platform", "Baggage Hall"),
            suspectDescriptions = mapOf(
                "Felix Ward" to "The senior signal operator who lent an old control cabinet to the charity display.",
                "Lena Moss" to "A conductor organizing the platform signs for the station's puzzle night.",
                "Samir Holt" to "A freight contractor famous for slipping jokes into staff notices."
            ),
            weaponDescriptions = mapOf(
                "Cabinet Key" to "The key to the display cabinet holding spare letter tiles.",
                "Chalk Marker" to "A thick marker used for temporary platform directions.",
                "Timetable Roll" to "A roll of printed strips cut into the silly destination names on the puzzle board."
            ),
            locationDescriptions = mapOf(
                "Control Room" to "The staff room where the borrowed display cabinet was signed out.",
                "Platform" to "The passenger platform being prepared with charity-event directions.",
                "Baggage Hall" to "The event space containing the mechanical puzzle board and spare timetable strips."
            ),
            clues = listOf(
                Clue("The Chalk Marker was on the Platform.", WeaponAt("Chalk Marker", "Platform")),
                Clue("Felix Ward was not on the Platform.", SuspectNotAt("Felix Ward", "Platform")),
                Clue("Lena Moss was not in the Baggage Hall.", SuspectNotAt("Lena Moss", "Baggage Hall")),
                Clue("The Cabinet Key was not in the Baggage Hall.", WeaponNotAt("Cabinet Key", "Baggage Hall")),
                Clue("Samir Holt was not in the Control Room.", SuspectNotAt("Samir Holt", "Control Room")),
                Clue("Felix Ward carried the Cabinet Key.", SuspectHas("Felix Ward", "Cabinet Key"))
            ),
            solutionSuspect = "Samir Holt",
            solutionWeapon = "Timetable Roll",
            solutionLocation = "Baggage Hall",
            explanation = "Felix had the Cabinet Key and could not be on the Platform, while the key was not in the Baggage Hall; he was therefore in the Control Room. Lena was not in the Baggage Hall, so she stood on the Platform with the Chalk Marker. Samir remained beside the charity puzzle board in the Baggage Hall with the Timetable Roll used to create the nonsense destinations."
        ),
        MysteryCase(
            id = 4,
            title = "The Cipher Club Mix-Up",
            difficulty = "Medium",
            story = "During an escape-room tournament, the master clue sheet vanished and the room's coded props were placed in the wrong cabinets. Three club members had access to the Archive, Cipher Room, and Map Room, each carrying a different prop. Find who hid the clue sheet, which prop they used, and where.",
            suspects = listOf("Iris Chen", "Noah Pike", "Elena March"),
            weapons = listOf("Brass Key", "Inked Glove", "Puzzle Reel"),
            locations = listOf("Archive", "Cipher Room", "Map Room"),
            suspectDescriptions = mapOf(
                "Iris Chen" to "The club archivist, protective of the tournament's carefully catalogued puzzle collection.",
                "Noah Pike" to "A cryptography enthusiast who thought the championship needed one final surprise.",
                "Elena March" to "A map-puzzle specialist responsible for arranging the route clues."
            ),
            weaponDescriptions = mapOf(
                "Brass Key" to "An old prop key that opens the Archive's puzzle cabinet.",
                "Inked Glove" to "A leather glove stained with transfer ink from the missing clue sheet.",
                "Puzzle Reel" to "A film-style reel holding a sequence of projected riddles."
            ),
            locationDescriptions = mapOf(
                "Archive" to "A climate-controlled store of retired riddles and tournament records.",
                "Cipher Room" to "The coded chamber where the final clue should have appeared.",
                "Map Room" to "A wide room of charts, route puzzles, and locked drawers."
            ),
            clues = listOf(
                Clue("Iris Chen was not in the Cipher Room.", SuspectNotAt("Iris Chen", "Cipher Room")),
                Clue("The Puzzle Reel was in the Map Room.", WeaponAt("Puzzle Reel", "Map Room")),
                Clue("The Brass Key was in the Archive.", WeaponAt("Brass Key", "Archive")),
                Clue("Elena March was not in the Archive.", SuspectNotAt("Elena March", "Archive")),
                Clue("Noah Pike wore the Inked Glove.", SuspectHas("Noah Pike", "Inked Glove"))
            ),
            solutionSuspect = "Noah Pike",
            solutionWeapon = "Inked Glove",
            solutionLocation = "Cipher Room",
            explanation = "The Brass Key and Puzzle Reel place their holders in the Archive and Map Room. Noah had neither because he wore the Inked Glove. Iris could not be in the Cipher Room and Elena could not be in the Archive, forcing Iris into the Archive, Elena into the Map Room, and Noah into the Cipher Room with the inked clue sheet."
        ),
        MysteryCase(
            id = 5,
            title = "The Golden Moustache",
            difficulty = "Medium",
            story = "At a gallery preview, someone added a gleaming but removable gold moustache to the mayor's portrait in the North Gallery and quietly swapped two exhibit labels. Exactly one witness is lying. Use the physical clues and statements to identify who decorated the portrait and who made the false statement.",
            suspects = listOf("Ravi Soren", "Claire Rowan", "Theo Wynn"),
            weapons = listOf("Miniature Bust", "Wax Crayon", "Gold Varnish Jar"),
            locations = listOf("Sculpture Hall", "North Gallery", "Restoration Lab"),
            suspectDescriptions = mapOf(
                "Ravi Soren" to "A sculptor who insisted the solemn portrait needed a sense of humour.",
                "Claire Rowan" to "The gallery curator checking replacement labels in the Restoration Lab.",
                "Theo Wynn" to "A restoration specialist with access to reversible finishes and the North Gallery display."
            ),
            weaponDescriptions = mapOf(
                "Miniature Bust" to "A small plaster study from the Sculpture Hall.",
                "Wax Crayon" to "A removable conservation crayon used to mark temporary labels.",
                "Gold Varnish Jar" to "A jar of washable gold varnish matching the portrait's new moustache."
            ),
            locationDescriptions = mapOf(
                "Sculpture Hall" to "A stone-floored hall of pedestals and statues.",
                "North Gallery" to "The exhibition room containing the decorated portrait.",
                "Restoration Lab" to "A ventilated workroom where temporary labels and reversible materials are prepared."
            ),
            clues = listOf(
                Clue("The Miniature Bust was in the Sculpture Hall.", WeaponAt("Miniature Bust", "Sculpture Hall")),
                Clue("Claire Rowan was in the Restoration Lab.", SuspectAt("Claire Rowan", "Restoration Lab"))
            ),
            statements = listOf(
                WitnessStatement("Ravi Soren", "Theo Wynn was in the North Gallery.", SuspectAt("Theo Wynn", "North Gallery")),
                WitnessStatement("Claire Rowan", "I carried the Gold Varnish Jar.", SuspectHas("Claire Rowan", "Gold Varnish Jar")),
                WitnessStatement("Theo Wynn", "Ravi Soren had the Miniature Bust.", SuspectHas("Ravi Soren", "Miniature Bust"))
            ),
            solutionSuspect = "Theo Wynn",
            solutionWeapon = "Gold Varnish Jar",
            solutionLocation = "North Gallery",
            solutionLiar = "Claire Rowan",
            explanation = "Ravi truthfully places Theo in the North Gallery, where the decorated portrait stood. Theo truthfully identifies Ravi with the Miniature Bust in the Sculpture Hall. Claire was fixed in the Restoration Lab, so her claim to have the Gold Varnish Jar is the one false statement. She had the Wax Crayon, leaving Theo beside the portrait with the washable gold varnish."
        ),
        MysteryCase(
            id = 6,
            title = "The Observatory Star Swap",
            difficulty = "Medium",
            story = "During a public comet viewing, the observatory briefly lost power and the projector returned with every constellation renamed after snacks and cartoon animals. Three staff members were in different parts of the building with objects removed from their usual places. Determine who arranged the celestial joke.",
            suspects = listOf("Meera Knox", "Jon Bell", "Sofia Reed"),
            weapons = listOf("Star Chart Tube", "Projector Remote", "Telescope Cap"),
            locations = listOf("Dome", "Generator Room", "Library"),
            suspectDescriptions = mapOf(
                "Meera Knox" to "An astronomer who secretly enjoys inventing ridiculous names for constellations.",
                "Jon Bell" to "The maintenance chief who controls the observatory's electrical and projection systems.",
                "Sofia Reed" to "A science journalist preparing a light-hearted feature about the comet night."
            ),
            weaponDescriptions = mapOf(
                "Star Chart Tube" to "A rigid tube holding the observatory's official constellation charts.",
                "Projector Remote" to "The remote used to load presentations through the backup controller in the Generator Room.",
                "Telescope Cap" to "A large protective cap from the Library's display telescope."
            ),
            locationDescriptions = mapOf(
                "Dome" to "The rotating telescope chamber beneath the open roof.",
                "Generator Room" to "The electrical room containing the backup power panel and emergency projector controller.",
                "Library" to "A circular reading room lined with astronomical records and a display telescope."
            ),
            clues = listOf(
                Clue("The Projector Remote was in the Generator Room.", WeaponAt("Projector Remote", "Generator Room")),
                Clue("Sofia Reed was in the Library.", SuspectAt("Sofia Reed", "Library")),
                Clue("Meera Knox was not in the Generator Room.", SuspectNotAt("Meera Knox", "Generator Room")),
                Clue("The Telescope Cap was not in the Dome.", WeaponNotAt("Telescope Cap", "Dome"))
            ),
            solutionSuspect = "Jon Bell",
            solutionWeapon = "Projector Remote",
            solutionLocation = "Generator Room",
            explanation = "Sofia's Library location and Meera's absence from the Generator Room leave Jon in the Generator Room with the Projector Remote. The Telescope Cap could not be in the Dome, so Sofia had it and Meera had the Star Chart Tube. Jon used the emergency projection controller during the brief reset to load the joke constellation names."
        ),
        MysteryCase(
            id = 7,
            title = "The Silent Auction Switcheroo",
            difficulty = "Hard",
            story = "A sealed-bid charity auction ended with the winning ledger replaced by a notebook of cartoons and several lot numbers stuck to the wrong objects. The building had been closed to the public. Determine who relabelled the auction, what they carried, and where they worked.",
            suspects = listOf("Darius Vale", "Emi Laurent", "Kian Mercer"),
            weapons = listOf("Ivory Gavel", "Bid Stamp", "Red Label Roll"),
            locations = listOf("Auction Hall", "Vault", "Loading Bay"),
            suspectDescriptions = mapOf(
                "Darius Vale" to "A rival auctioneer who thought the event had become far too serious.",
                "Emi Laurent" to "An art dealer responsible for checking the sealed bids and lot paperwork.",
                "Kian Mercer" to "The venue owner who designed the auction's colour-coded label system."
            ),
            weaponDescriptions = mapOf(
                "Ivory Gavel" to "The ceremonial gavel used to close the final lot.",
                "Bid Stamp" to "A bright stamp normally used to mark accepted bid forms.",
                "Red Label Roll" to "A roll of removable red labels matching the switched lot numbers."
            ),
            locationDescriptions = mapOf(
                "Auction Hall" to "The main hall of numbered seats and display lights.",
                "Vault" to "A secure room containing documents and unsold display items.",
                "Loading Bay" to "A concrete service area where lots are labelled before entering the hall."
            ),
            clues = listOf(
                Clue("The Ivory Gavel was not in the Vault.", WeaponNotAt("Ivory Gavel", "Vault")),
                Clue("The Bid Stamp was in the Vault.", WeaponAt("Bid Stamp", "Vault")),
                Clue("Darius Vale was not in the Loading Bay.", SuspectNotAt("Darius Vale", "Loading Bay")),
                Clue("Emi Laurent was not in the Auction Hall.", SuspectNotAt("Emi Laurent", "Auction Hall")),
                Clue("The Red Label Roll was not in the Auction Hall.", WeaponNotAt("Red Label Roll", "Auction Hall")),
                Clue("Darius Vale was in the Auction Hall.", SuspectAt("Darius Vale", "Auction Hall")),
                Clue("Kian Mercer was not in the Vault.", SuspectNotAt("Kian Mercer", "Vault"))
            ),
            solutionSuspect = "Kian Mercer",
            solutionWeapon = "Red Label Roll",
            solutionLocation = "Loading Bay",
            explanation = "Darius was in the Auction Hall, and the Red Label Roll was not there, so he had the Ivory Gavel. Kian was not in the Vault, forcing Emi into the Vault with the Bid Stamp. Kian remained in the Loading Bay with the Red Label Roll used to switch the lot numbers before the objects entered the hall."
        ),
        MysteryCase(
            id = 8,
            title = "The Snowbound Snowman",
            difficulty = "Hard",
            story = "An avalanche kept everyone inside a mountain lodge, where someone built a giant indoor snowman from sofa cushions and dressed it in the owner's prized red scarf. Exactly one guest is lying. Work out who created the surprise display, what they carried, and where.",
            suspects = listOf("Anika Frost", "Bennett Cole", "Celia Hart"),
            weapons = listOf("Ski Wax Tin", "Red Scarf", "Cookie Tin"),
            locations = listOf("Equipment Room", "Great Room", "Pantry"),
            suspectDescriptions = mapOf(
                "Anika Frost" to "A climbing guide who had been teaching guests to wax their skis.",
                "Bennett Cole" to "The owner's business partner and an enthusiastic builder of ridiculous snow figures.",
                "Celia Hart" to "A travel writer collecting cheerful stories about life inside the snowbound lodge."
            ),
            weaponDescriptions = mapOf(
                "Ski Wax Tin" to "A round tin returned to the Equipment Room after the afternoon lesson.",
                "Red Scarf" to "The owner's prized scarf, now wrapped around the cushion snowman.",
                "Cookie Tin" to "A patterned tin from the Pantry, emptied during the snowman-building session."
            ),
            locationDescriptions = mapOf(
                "Equipment Room" to "A cold room of skis, waxes, ropes, and climbing gear.",
                "Great Room" to "The lodge's central room, where the cushion snowman was displayed.",
                "Pantry" to "A narrow food store behind the kitchen."
            ),
            clues = listOf(
                Clue("The Ski Wax Tin was in the Equipment Room.", WeaponAt("Ski Wax Tin", "Equipment Room")),
                Clue("The Cookie Tin was in the Pantry.", WeaponAt("Cookie Tin", "Pantry"))
            ),
            statements = listOf(
                WitnessStatement("Anika Frost", "Bennett Cole was in the Great Room.", SuspectAt("Bennett Cole", "Great Room")),
                WitnessStatement("Bennett Cole", "Celia Hart carried the Red Scarf.", SuspectHas("Celia Hart", "Red Scarf")),
                WitnessStatement("Celia Hart", "Anika Frost had the Ski Wax Tin.", SuspectHas("Anika Frost", "Ski Wax Tin"))
            ),
            solutionSuspect = "Bennett Cole",
            solutionWeapon = "Red Scarf",
            solutionLocation = "Great Room",
            solutionLiar = "Bennett Cole",
            explanation = "The Ski Wax Tin and Cookie Tin fix their holders in the Equipment Room and Pantry. Celia truthfully places the Ski Wax Tin with Anika, and Anika truthfully places Bennett in the Great Room. Bennett's claim that Celia had the Red Scarf is false: Celia had the Cookie Tin, leaving Bennett with the scarf used to finish the cushion snowman."
        ),
        MysteryCase(
            id = 9,
            title = "The Clockmaker's Mixed-Up Hour",
            difficulty = "Hard",
            story = "Before the town clock exhibition, every clock was reset to 13:13 and the prototype cuckoo was replaced with a toy duck. Three associates were found in separate rooms carrying controls or components moved from different clocks. Determine who staged the timekeeping prank.",
            suspects = listOf("Leo Grant", "Maya Rook", "Omar Venn"),
            weapons = listOf("Winding Key", "Steel Spring", "Master Timer Dial"),
            locations = listOf("Workshop", "Showroom", "Office"),
            suspectDescriptions = mapOf(
                "Leo Grant" to "The clockmaker's apprentice, responsible for winding the exhibition pieces.",
                "Maya Rook" to "A collector who donated the toy duck as a joke gift.",
                "Omar Venn" to "A competing clockmaker who claimed every exhibition needed one impossible time."
            ),
            weaponDescriptions = mapOf(
                "Winding Key" to "An oversized brass key from the exhibition prototype.",
                "Steel Spring" to "A loose mainspring removed during routine maintenance.",
                "Master Timer Dial" to "The Office control dial used to synchronize every demonstration clock."
            ),
            locationDescriptions = mapOf(
                "Workshop" to "The crowded bench room where the prototype stood.",
                "Showroom" to "A polished gallery of ticking display clocks.",
                "Office" to "The private room containing the exhibition's master timing controls."
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
            solutionWeapon = "Master Timer Dial",
            solutionLocation = "Office",
            explanation = "Leo's Winding Key puts him in the Workshop. Maya was not in the Office and Leo was not in the Showroom, so Maya occupied the Showroom and Omar the Office. The Steel Spring was not in the Office, leaving it with Maya and the Master Timer Dial with Omar, who used the Office controls to synchronize every clock to 13:13."
        ),
        MysteryCase(
            id = 10,
            title = "The Submarine Sea-Monster Signal",
            difficulty = "Hard",
            story = "A research submarine transmitted a cheerful false alert claiming that a sea monster had joined the expedition, while the Radio Room played whale songs over the crew channel. Exactly one crew member is lying about the final minutes before the broadcast.",
            suspects = listOf("Nadia Shore", "Eli Mercer", "Tomas Reed"),
            weapons = listOf("Signal Flag", "Tool Pouch", "Cable Loop"),
            locations = listOf("Bridge", "Engine Room", "Radio Room"),
            suspectDescriptions = mapOf(
                "Nadia Shore" to "The first officer, known for running very formal bridge drills.",
                "Eli Mercer" to "The chief engineer who maintains a collection of novelty warning signs.",
                "Tomas Reed" to "The communications specialist and the crew's unofficial sound-effects expert."
            ),
            weaponDescriptions = mapOf(
                "Signal Flag" to "A bright emergency flag stored beside the Bridge hatch.",
                "Tool Pouch" to "A canvas pouch used for routine Engine Room adjustments.",
                "Cable Loop" to "A spare communications cable looped beside the Radio Room console."
            ),
            locationDescriptions = mapOf(
                "Bridge" to "The command compartment containing navigation controls.",
                "Engine Room" to "The loud machinery compartment at the stern.",
                "Radio Room" to "The communications compartment where the playful signal originated."
            ),
            clues = listOf(
                Clue("The Signal Flag was on the Bridge.", WeaponAt("Signal Flag", "Bridge")),
                Clue("The Tool Pouch was in the Engine Room.", WeaponAt("Tool Pouch", "Engine Room"))
            ),
            statements = listOf(
                WitnessStatement("Nadia Shore", "Eli Mercer was in the Engine Room.", SuspectAt("Eli Mercer", "Engine Room")),
                WitnessStatement("Eli Mercer", "Tomas Reed carried the Tool Pouch.", SuspectHas("Tomas Reed", "Tool Pouch")),
                WitnessStatement("Tomas Reed", "Nadia Shore had the Signal Flag.", SuspectHas("Nadia Shore", "Signal Flag"))
            ),
            solutionSuspect = "Tomas Reed",
            solutionWeapon = "Cable Loop",
            solutionLocation = "Radio Room",
            solutionLiar = "Eli Mercer",
            explanation = "Tomas truthfully places the Signal Flag with Nadia on the Bridge, and Nadia truthfully places Eli in the Engine Room with the Tool Pouch. Eli's claim that Tomas had that pouch is therefore the one false statement. Tomas was in the Radio Room with the Cable Loop and used the console to send the sea-monster alert and whale-song broadcast."
        )
    )
}
