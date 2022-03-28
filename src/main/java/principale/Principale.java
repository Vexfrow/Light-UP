package principale;
import java.io.IOException;

import principale.Formule;

public class Principale{

//Classe principale du projet

    public static void main (String[] args) throws IOException{
        Formule f = new Formule("(1 | 2 | 3 | 4 |8) & (1 | 2 | 3 | 4 | 9 | 16 |23 | 30 | 37 | 44) & (1 | 2 | 3 | 4 | 10 | 17) & (1 | 2 | 3 | 4 | 11) & (6 | 7 | 13 | 20 | 27 | 34 | 41 | 48 ) & (6 | 7 | 14 | 21 | 28) & (1 | 8 | 9 | 10 |11 | 12|12|14) & (2 | 8 | 9 | 10 |11 | 12|12|14|16|23|30|37|44) & (3 | 8 | 9 | 10 |11 | 12|12|14 |17)&(4| 8 | 9 | 10 |11 | 12|12|14) & (8 | 9 | 10 |11 | 12|12|14|19) & (6| 8 | 9 | 10 |11 | 12|12|14|20|27|34|41|48)&(7 | 8 | 9 | 10 |11 | 12|12|14|21|28) & (2| 9 | 16 |17 | 23|30|37|44) & (3| 10 | 16 |17) & (12| 19 |20|21)&(6| 13 | 19 |20 | 21|27|34|41|48) & (7| 14 | 19 |17 | 20|21|28) & (22|23|29|36|43) & (2| 9 | 16 |22 | 23|30|37|44) & (25)& (6|13|20 |27|28|34|41|48) & (7|14|21|27|28) & (22| 29 | 30 |31| 36|43) & (2| 9 | 16| 23|29|30|31|37|44) & (29|30| 31|38) & (33|34|40|47) & (6|13|20|27|33|34|41|48) & (22|29|36|37|38|39|40|41|42|43)& (2|9|16|23|30|36|37|38|40|41|42|44) & (31| 36 | 37 |38 | 39|40|41|42) & (36| 37 | 38 |39 | 40|41|42|46) & (33| 36 | 37 |38 | 39|40|41|42|47)& (6|13|20|27|34|36|37|38|39|40|41|42|48) & (36|37|38|39|40|41|42|48) & (22|29|36|43|44) & (2|9|16|23|30|37|43|44)& (39|46|47|48|49) & (33|40|46|47|48|49) & (6|13|20|27|34|41|46|47|48|49) & (42|46|47|48|49)& (!5) & (!15) & (!18) & (!24) & (!26) & (!32) & (!35) & (!45) & (!8 | !16) & (!8 | !22) & (8 | 16 | 22) & (!16 | !22) & (!11 |!17) & (!11 | !19) & (!11 | !25) & (11 | 17 | 19 | 25)& (!17 | !19) & (!17 | !25) & (!19 | !25) & (!19 | !25 | !27) & (!19 | !25 | !33) & (!19 | !27 | !33) & (19 | 25 |27)& (19 | 25 |33) & (19 |27|33) & (!25 |!27 | !33) & (25 | 27 | 33) & (!25 | !31 | !33) & (!25 | !31 | !39) & (!25 | !33 | !39) & (25 | 31 | 33) & (25 | 31 |39) & (25 | 33 | 39) & (!31 | !33 |!39) & (31 | 33 | 39) & (!28 | !34 | !42) & (28 | 34)& (28 | 42) & (34 | 42) & (!38 | !44 | !46) & (38 | 44) & (38 | 46) & (44 | 46)& (!1 | !2) & (!1 | !3) & (!1 | !4) & (!2 | !3) & (!2 | !4) & (!2 | !9) & (!2 | !16) & (!2 | !23) & (!2 | !30) & (!2 | !37) & (!2 |!44) & (!3 | !4) & (!3 | !10) & (!3 | !17) & (!4 | !11)& (!6 | !7) & (!6 | !13) & (!6 | !20) & (!6 | !27) & (!6 | !34) & (!6 | !41) & (!6 | !48) & (!7 | !14) & (!7 | !21) & (!7 | !28)& (!8 | !9) & (!8 | !10) & (!8 | !11) & (!8 | !12) & (!8 | !13) & (!8 | !14) & (!9 | !10) & (!9 | !11) & (!9 | !12) & (!9 | !13) & (!9 | !14)& (!9 | !16) & (!9 | !23) & (!9 | !30) & (!9 | !37) & (!9 |!44) & (!10 | !11) & (!10 | !12) & (!10 | !13) & (!10 | !14)& (!10|!17) & (!11 | !12) & (!11 | !13) & (!11 | !14) & (!12 | !13) & (!12 | !14) & (!12 | !19) & (!13 | !14)& (!13 | !20) & (!13 | !27) & (!13 | !34) & (!13 | !41) & (!13 | !48) & (!14 | !21) & (!14 | !28)& (!16 |!17) & (!16 | !23) & (!16 | !30) & (!16 | !37) & (!16 |!44) & (!19 | !20) & (!19 | !21) & (!20 | !21)& (!20 | !27) & (!20 | !34) & (!20 | !41) & (!20 | !48) & (!21 | !28) & (!22 | !23) & (!22 | !29) & (!22 |!36) & (!22 |!43)& (!23 | !30) & (!23 | !37) & (!23|!44) & (!27 | !28) & (!27 | !34) & (!27 | !41) & (!27 | !48)& (!29 | !30) & (!29 | !31) & (!29 |!36) & (!29 |!43) & (!30 | !31) & (!30 | !37) & (!30 |!44) & (!31 |!38) & (!33 |!34)& (!33 |!40) & (!33 |!47) & (!34 |!41) & (!34 |!48) & (!36 |!37) & (!36 |!38) & (!36 |!39) & (!36 |!40) & (!36 |!41) & (!36 |!42)& (!36 |!43) & (!37 |!38) & (!37 |!39) & (!37 |!40) & (!37 |!41) & (!37 |!42) & (!37 |!44) & (!38 |!39) & (!38 |!40) & (!38 |!41) & (!38 |!42)& (!39 |!40) & (!39 |!41) & (!39 |!42) & (!39 |!46) & (!40 |!41) & (!40 |!42) & (!40 |!47) & (!41 |!42) & (!41 |!48) & (!42 |!49)& (!43 |!44) & (!46 |!47) & (!46 |!48) & (!46 |!49) & (!47 |!48) & (!47 |!49) & (!48 |!49)");
        f.ecrireFormule();

    }


}