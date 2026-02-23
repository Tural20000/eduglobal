/**
 * Günün sözü – hər gün eyni tarix üçün eyni söz (vocabulary səviyyələrindən random).
 * Səviyyə və söz tarix əsaslı "seed" ilə seçilir ki, gün ərzində sabit qalsın.
 */
(function () {
    const wordsA1 = [
        { en: "Always", az: "Həmişə" }, { en: "Beautiful", az: "Gözəl" }, { en: "Big", az: "Böyük" },
        { en: "Book", az: "Kitab" }, { en: "Breakfast", az: "Səhər yeməyi" }, { en: "Brother", az: "Qardaş" },
        { en: "City", az: "Şəhər" }, { en: "Clean", az: "Təmiz" }, { en: "Cold", az: "Soyuq" },
        { en: "Daily", az: "Gündəlik" }, { en: "Daughter", az: "Qız (övlad)" }, { en: "Day", az: "Gün" },
        { en: "Drink", az: "İçmək" }, { en: "Eat", az: "Yemək" }, { en: "Family", az: "Ailə" },
        { en: "Friend", az: "Dost" }, { en: "Garden", az: "Bağça" }, { en: "Happy", az: "Xoşbəxt" },
        { en: "House", az: "Ev" }, { en: "Important", az: "Vacib" }, { en: "Kitchen", az: "Mətbəx" },
        { en: "Learn", az: "Öyrənmək" }, { en: "Listen", az: "Dinləmək" }, { en: "Morning", az: "Səhər" },
        { en: "Name", az: "Ad" }, { en: "New", az: "Yeni" }, { en: "Night", az: "Gecə" },
        { en: "Often", az: "Tez-tez" }, { en: "Paper", az: "Kağız" }, { en: "Question", az: "Sual" },
        { en: "Read", az: "Oxumaq" }, { en: "School", az: "Məktəb" }, { en: "Sister", az: "Bacı" },
        { en: "Small", az: "Kiçik" }, { en: "Study", az: "Oxumaq (təhsil)" }, { en: "Table", az: "Masa" },
        { en: "Teacher", az: "Müəllim" }, { en: "Time", az: "Vaxt" }, { en: "Under", az: "Altında" },
        { en: "Village", az: "Kənd" }, { en: "Water", az: "Su" }, { en: "Week", az: "Həftə" },
        { en: "Write", az: "Yazmaq" }, { en: "Year", az: "İl" }, { en: "Young", az: "Gənc" }
    ];
    const wordsB1 = [
        { en: "Accurate", az: "Dəqiq" }, { en: "Advantage", az: "Üstünlük" }, { en: "Agreement", az: "Razılaşma" },
        { en: "Ambition", az: "Ambiya/İstək" }, { en: "Ancient", az: "Qədim" }, { en: "Appointment", az: "Görüş/Təyinat" },
        { en: "Appropriate", az: "Münasib" }, { en: "Artificial", az: "Süni" }, { en: "Average", az: "Ortalama" },
        { en: "Baggage", az: "Baqaj" }, { en: "Behavior", az: "Davranış" }, { en: "Benefit", az: "Fayda" },
        { en: "Boundary", az: "Sərhəd" }, { en: "Business", az: "Biznes/İş" }, { en: "Candidate", az: "Namizəd" },
        { en: "Celebrate", az: "Qeyd etmək" }, { en: "Challenge", az: "Sınaq/Çətinlik" }, { en: "Citizen", az: "Vətəndaş" },
        { en: "Conclusion", az: "Nəticə" }, { en: "Confidence", az: "Güvən" }, { en: "Convenient", az: "Rahat/Münasib" },
        { en: "Decision", az: "Qərar" }, { en: "Delicious", az: "Ləzzətli" }, { en: "Description", az: "Təsvir" },
        { en: "Education", az: "Təhsil" }, { en: "Effective", az: "Effektiv" }, { en: "Environment", az: "Ətraf mühit" },
        { en: "Evidence", az: "Sübut" }, { en: "Experience", az: "Təcrübə" }, { en: "Fashionable", az: "Dəbdə olan" },
        { en: "Frequency", az: "Tezlik" }, { en: "Furniture", az: "Mebel" }, { en: "Generation", az: "Nəsil" },
        { en: "Government", az: "Hökumət" }, { en: "Gradually", az: "Tədricən" }, { en: "Health", az: "Sağlamlıq" },
        { en: "Identity", az: "Kimlik" }, { en: "Improvement", az: "Təkmilləşmə" }, { en: "Influence", az: "Təsir" },
        { en: "Knowledge", az: "Bilik" }, { en: "Landscape", az: "Mənzərə" }, { en: "Management", az: "İdarəetmə" },
        { en: "Necessary", az: "Vacib" }, { en: "Opportunity", az: "Fürsət" }, { en: "Patient", az: "Səbirli" },
        { en: "Pollution", az: "Çirklənmə" }, { en: "Quality", az: "Keyfiyyət" }, { en: "Reasonable", az: "Ağıllı/Məntiqli" },
        { en: "Relationship", az: "Münasibət" }, { en: "Situation", az: "Vəziyyət" }, { en: "Successful", az: "Uğurlu" },
        { en: "Tradition", az: "Ənənə" }, { en: "Understand", az: "Anlamaq" }, { en: "Valuable", az: "Qiymətli" }
    ];
    const wordsC1 = [
        { en: "Ambiguous", az: "İkimənalı / Qeyri-müəyyən" }, { en: "Anomalous", az: "Anomal / Qaydadan kənar" },
        { en: "Articulate", az: "Fikrini aydın ifadə edən" }, { en: "Benevolent", az: "Xeyirxah / Mərhəmətli" },
        { en: "Capricious", az: "Şıltaq / Dəyişkən" }, { en: "Cognitive", az: "Koqnitiv / İdrakla bağlı" },
        { en: "Comprehensive", az: "Hərtərəfli / Geniş" }, { en: "Conundrum", az: "Tapmaca / Qəliz məsələ" },
        { en: "Dichotomy", az: "Dixotomiya / İki zidd hissəyə bölünmə" }, { en: "Eloquent", az: "Natiq / Bəlağətli" },
        { en: "Ephemeral", az: "Müvəqqəti / Gəldi-gedər" }, { en: "Exacerbate", az: "Kəskinləşdirmək / Pisləşdirmək" },
        { en: "Frivolous", az: "Yüngül / Ciddi olmayan" }, { en: "Gregarious", az: "Ünsiyyətcil / Yarımcan" },
        { en: "Hypothetical", az: "Ehtimallı / Hipotetik" }, { en: "Immaculate", az: "Ləkəsiz / Tərtəmiz" },
        { en: "Incentive", az: "Həvəsləndirmə / Stimul" }, { en: "Inevitably", az: "Qaçılmaz olaraq" },
        { en: "Innate", az: "Anadangəlmə / Daxili" }, { en: "Juxtaposition", az: "Yan-yana qoyma (müqayisə üçün)" },
        { en: "Lethargic", az: "Süst / Keyləşmiş" }, { en: "Magnanimous", az: "Alicənab / Əliaçıq" },
        { en: "Meticulous", az: "Həddindən artıq diqqətli" }, { en: "Nefarious", az: "Mənfur / Çox pis" },
        { en: "Obsolete", az: "Köhnəlmiş / İstifadədən çıxmış" }, { en: "Paradigm", az: "Paradigma / Nümunə" },
        { en: "Plausible", az: "Ağlabatan / İnandırıcı" }, { en: "Pragmatic", az: "Praqmatik / Realist" },
        { en: "Resilient", az: "Dözümlü / Elastik" }, { en: "Scrutinize", az: "Diqqətlə araşdırmaq" },
        { en: "Substantial", az: "Əhəmiyyətli / Böyük" }, { en: "Superfluous", az: "Artıq / Lazımsız" },
        { en: "Taciturn", az: "Azdanışan / Sakit" }, { en: "Ubiquitous", az: "Hər yerdə rast gəlinən" },
        { en: "Venerable", az: "Hörmətli / Möhtərəm" }, { en: "Whimsical", az: "Qəribə / İnancsız" },
        { en: "Zealous", az: "Şövqlü / Qeyrətli" }, { en: "Abstain", az: "Çəkinmək / İmtina etmək" },
        { en: "Adversity", az: "Bədbəxtlik / Çətinlik" }, { en: "Collaborate", az: "Əməkdaşlıq etmək" }
    ];

    const levels = [
        { name: 'A1', words: wordsA1 },
        { name: 'B1', words: wordsB1 },
        { name: 'C1', words: wordsC1 }
    ];

    /** Tarixə görə sadə “seed” – hər gün eyni rəqəm */
    function daySeed() {
        var d = new Date();
        var y = d.getFullYear(), m = d.getMonth() + 1, day = d.getDate();
        return (y * 372 + m * 31 + day) >>> 0;
    }

    /** Günün sözünü qaytarır: { en, az, level } */
    function getWordOfTheDay() {
        var seed = daySeed();
        var levelIndex = seed % levels.length;
        var level = levels[levelIndex];
        var wordIndex = (seed >> 5) % level.words.length;
        var word = level.words[wordIndex];
        return { en: word.en, az: word.az, level: level.name };
    }

    window.getWordOfTheDay = getWordOfTheDay;
})();
