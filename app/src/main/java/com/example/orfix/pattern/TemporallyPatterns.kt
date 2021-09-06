package com.example.orfix.pattern

object TemporallyPatterns {
	// rhythm
	var straightRhythm1: String? = "32|32+0:32"
	var straightRhythm2 = "32|32+0:16"
	var straightRhythm3 = "32|128+0:128"
	var rhythm4 = "32|128+24:24|64:12|96:16"
	var rhythm5 = "32|128+0:16|24:24|64:12|96:16"

	var standard_whole = "32|128+0:128"
	var standard_half = "32|64+0:64"
	var standard_quarter = "32|32+0:32"
	var standard_quarter_trio = "32|64+0:21|21:21|42:22"
	var standard_eight = "32|16+0:16"
	var standard_eight_trio = "32|32+0:11|11:11|22:10"
	var standard_sixteens = "32|8+0:8"
	var standard_thirty_two = "32|4+0:4"

	var short_1to4_01 = "32|32+0:16"
	var short_1to4_02 = "32|32+16:16"
	var short_1to4_03 = "32|32+0:8"
	var short_1to4_04 = "32|32+8:16"
	var short_1to4_05 = "32|32+8:8"
	var short_1to4_06 = "32|32+0:6|8:6"
	var short_1to4_07 = "32|32+16:6|24:6"
	var short_1to4_08 = "32|32+0:6|8:6|16:6"
	var short_1to4_09 = "32|32+8:6|16:6|24:6"
	var short_1to4_10 = "32|32+0:6|8:6|24:6"
	var short_1to4_11 = "32|32+0:6|16:6|24:6"
	var short_1to4_12 = "32|32+0:14|16:6|24:6"
	var short_1to4_13 = "32|32+0:6|8:14|24:6"
	var short_1to4_14 = "32|32+0:22|24:6"
	var short_1to4_15 = "32|32+0:14|24:6"
	var short_1to4_16 = "32|32+0:16|24:4|28:4"
	var short_1to4_17 = "32|32+0:16|20:8"
	var short_1to4_18 = "32|32+8:8|20:8"
	var short_1to4_19 = "32|32+0:8|12:8|24:8"
	var short_1to4_20 = "32|32+0:8|16:4|24:8"
	var short_1to4_21 = "32|32+0:4|8:8|24:8"
	var short_1to4_22 = "32|32+0:4|8:8|16:4|24:8"
	var short_1to4_23 = "32|32+0:8|8:4|16:8|24:4"
	var short_1to4_24 = "32|32+0:4|12:4|16:14"
	var short_1to4_25 = "32|32+0:2|12:8|20:12"

	var scratch_1 = "32|32+0:1|2:2|4:6|10:10|20:12"
	var scratch_2 = "32|32+0:1|2:1|3:1|4:4|8:4|12:20"
	var scratch_3 = "32|32+0:12|12:10|22:6|28:2|30:1"
	var scratch_4 = "32|32+0:20|20:4|24:4|28:1|29:1|30:1|31:1"
	var scratch_5 = "32|32+0:4|4:4|8:1|9:1|10:2|12:1|13:1|14:2|16:16"
	var scratch_6 = "32|32+0:1|1:2|3:3|6:4|10:5|15:6|21:7|28:4"
	var scratch_7 = "32|32+0:4|4:7|11:6|17:5|22:4|26:3|29:2|31:1"

	var middle_1_to_2_1 = "32|64+0:16|24:8|40:54|56:64"
	var middle_1_to_2_2 = "32|64+0:16|24:24|48:16"
	var middle_1_to_2_3 = "32|64+0:16|24:16|40:24"
	var middle_1_to_2_4 = "32|64+0:16|24:8|40:54|56:64"

	var long_1 = "32|128+0:64|96:32"
	var long_2 = "32|128+0:96"
	var long_3 = "32|128+0:32|64:64"
	var long_4 = "32|128+0:96|96:32"
	var long_5 = "32|128+0:32|32:96"


	// interval
	var drawing1: String? = "1:0|0:6|0:5|0:4"
	var drawing2 = "0:0|-1:5|-1:6|-1:4"


	// harmony
	var thirds_normal: String? = "0:0|0:2|0:4"
	var thirds_rev_1 = "0:2|0:4|1:0"
	var thirds_rev_2 = "0:4|1:0|1:2"
	var power = "0:0|0:4"
	var power_double = "0:0|0:4|1:0|1:4"
	var power_additional_t = "0:0|0:4|1:0|1:2"
	var power_additional_s = "0:0|0:4|1:0|1:1"
	var sept_normal = "0:0|0:2|0:4|0:6"
	var sept_rev_1 = "1:0|0:2|0:4|0:6"
	var sept_rev_2 = "1:0|1:2|0:4|0:6"
	var sept_rev_3 = "1:0|1:2|1:4|0:6"
}