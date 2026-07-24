package com.example.util

object Localization {
    
    private val translations: Map<String, Map<String, String>> = mapOf(
        // Navigation Bar
        "nav_home" to mapOf(
            "English" to "Home", "Tamil" to "முகப்பு", "Hindi" to "होम",
            "Telugu" to "హోమ్", "Malayalam" to "ഹോം", "Marathi" to "मुख्य",
            "Kannada" to "ಮುಖಪುಟ", "Gujarati" to "હોમ", "Punjabi" to "ਹੋਮ", "Bengali" to "হোম"
        ),
        "nav_scan" to mapOf(
            "English" to "Scan", "Tamil" to "ஸ்கேன்", "Hindi" to "स्कैन",
            "Telugu" to "స్కాన్", "Malayalam" to "സ്കാൻ", "Marathi" to "स्कॅन",
            "Kannada" to "ಸ್ಕ್ಯಾನ್", "Gujarati" to "સ્કેન", "Punjabi" to "ਸਕੈਨ", "Bengali" to "স্ক্যান"
        ),
        "nav_crops" to mapOf(
            "English" to "Crops", "Tamil" to "பயிர்கள்", "Hindi" to "फसलें",
            "Telugu" to "పంటలు", "Malayalam" to "വിളകൾ", "Marathi" to "पिके",
            "Kannada" to "ಬೆಳೆಗಳು", "Gujarati" to "પાકો", "Punjabi" to "ਫ਼ਸਲਾਂ", "Bengali" to "ফসল"
        ),
        "nav_yield" to mapOf(
            "English" to "Yield", "Tamil" to "மகசூல்", "Hindi" to "उपज",
            "Telugu" to "దిగుబడి", "Malayalam" to "വിളവ്", "Marathi" to "उत्पादन",
            "Kannada" to "ಇಳುವರಿ", "Gujarati" to "ઉપજ", "Punjabi" to "ਪੈਦਾਵਾਰ", "Bengali" to "ফলন"
        ),
        "nav_forum" to mapOf(
            "English" to "Forum", "Tamil" to "மன்றம்", "Hindi" to "मंच",
            "Telugu" to "వేదిక", "Malayalam" to "ഫോറം", "Marathi" to "मंच",
            "Kannada" to "ವೇದಿಕೆ", "Gujarati" to "મંચ", "Punjabi" to "ਮੰਚ", "Bengali" to "মঞ্চ"
        ),
        "nav_chat" to mapOf(
            "English" to "AI Chat", "Tamil" to "AI சாட்", "Hindi" to "AI चैट",
            "Telugu" to "AI చాట్", "Malayalam" to "AI ചാറ്റ്", "Marathi" to "AI चॅट",
            "Kannada" to "AI ಚಾಟ್", "Gujarati" to "AI ચેટ", "Punjabi" to "AI ਚੈਟ", "Bengali" to "AI চ্যাট"
        ),
        "nav_market" to mapOf(
            "English" to "Market", "Tamil" to "சந்தை", "Hindi" to "मंडी",
            "Telugu" to "మార్కెట్", "Malayalam" to "മാർക്കറ്റ്", "Marathi" to "बाजार",
            "Kannada" to "ಮಾರುಕಟ್ಟೆ", "Gujarati" to "માર્કેટ", "Punjabi" to "ਮੰਡੀ", "Bengali" to "বাজার"
        ),
        "nav_profile" to mapOf(
            "English" to "Profile", "Tamil" to "சுயவிவரம்", "Hindi" to "प्रोफाइल",
            "Telugu" to "ప్రొఫైల్", "Malayalam" to "പ്രൊഫൈൽ", "Marathi" to "प्रोफाइल",
            "Kannada" to "ಪ್ರೊಫೈಲ್", "Gujarati" to "પ્રોફાઇલ", "Punjabi" to "ਪ੍ਰੋਫਾਈਲ", "Bengali" to "প্রোফাইল"
        ),

        // App Subtitle
        "app_subtitle" to mapOf(
            "English" to "Precision Farming & AI Crop Doctor",
            "Tamil" to "துல்லிய விவசாயம் & AI பயிர் மருத்துவர்",
            "Hindi" to "सटीक कृषि और एआई फसल डॉक्टर",
            "Telugu" to "ఖచ్చితమైన వ్యవసాయం & AI పంట డాక్టర్",
            "Malayalam" to "കൃത്യതാ കൃഷിയും AI വിള ഡോക്ടറും",
            "Marathi" to "अचूक शेती आणि AI पीक डॉक्टर",
            "Kannada" to "ನಿಖರ ಕೃಷಿ ಮತ್ತು AI ಬೆಳೆ ವೈದ್ಯ",
            "Gujarati" to "ચોક્કસ ખેતી અને AI પાક ડોક્ટર",
            "Punjabi" to "ਸਟੀਕ ਖੇਤੀਬਾੜੀ ਅਤੇ AI ਫਸਲ ਡਾਕਟਰ",
            "Bengali" to "সঠিক কৃষি ও AI ফসল চিকিৎসক"
        ),

        // Dashboard Greetings & Header
        "greeting_prefix" to mapOf(
            "English" to "Namaste", "Tamil" to "வணக்கம்", "Hindi" to "नमस्ते",
            "Telugu" to "నమస్కారం", "Malayalam" to "നമസ്കാരം", "Marathi" to "नमस्कार",
            "Kannada" to "ನಮಸ್ಕಾರ", "Gujarati" to "નમસ્તે", "Punjabi" to "ਸਤਿ ਸ਼੍ਰੀ ਅਕਾਲ", "Bengali" to "নমস্কার"
        ),
        "active_plots" to mapOf(
            "English" to "Active Plots", "Tamil" to "செயலில் உள்ள நிலங்கள்", "Hindi" to "सक्रिय खेत",
            "Telugu" to "క్రియాశీల పొలాలు", "Malayalam" to "സജീവ പ്ലോട്ടുകൾ", "Marathi" to "सक्रिय शेत",
            "Kannada" to "ಸಕ್ರಿಯ ಜಮೀನುಗಳು", "Gujarati" to "સક્રિય ખેતરો", "Punjabi" to "ਐਕਟਿਵ ਪਲਾਟ", "Bengali" to "সক্রিয় জমি"
        ),
        "crop_health" to mapOf(
            "English" to "Crop Health", "Tamil" to "பயிர் ஆரோக்கியம்", "Hindi" to "फसल स्वास्थ्य",
            "Telugu" to "పంట ఆరోగ్యం", "Malayalam" to "വിള ആരോഗ്യം", "Marathi" to "पीक आरोग्य",
            "Kannada" to "ಬೆಳೆ ಆರೋಗ್ಯ", "Gujarati" to "પાક તંદુરસ્તી", "Punjabi" to "ਫਸਲ ਦੀ ਸਿਹਤ", "Bengali" to "ফসলের স্বাস্থ্য"
        ),
        "soil_temp" to mapOf(
            "English" to "Soil Temp", "Tamil" to "மண் வெப்பநிலை", "Hindi" to "मिट्टी का तापमान",
            "Telugu" to "నేల ఉష్ణోగ్రత", "Malayalam" to "മണ്ണ് താപനില", "Marathi" to "मातीचे तापमान",
            "Kannada" to "ಮಣ್ಣಿನ ತಾಪಮಾನ", "Gujarati" to "જમીનનું તાપમાન", "Punjabi" to "ਮਿੱਟੀ ਦਾ ਤਾਪਮਾਨ", "Bengali" to "মাটির তাপমাত্রা"
        ),

        // Weather
        "live_weather" to mapOf(
            "English" to "Live Agri Weather", "Tamil" to "நேரலை வேளாண் வானிலை", "Hindi" to "लाइव कृषि मौसम",
            "Telugu" to "లైవ్ వ్యవసాయ వాతావరణం", "Malayalam" to "തത്സമയ കാലാവസ്ഥ", "Marathi" to "थेट हवामान",
            "Kannada" to "ನೇರ ಕೃಷಿ ಹವಾಮಾನ", "Gujarati" to "લાઈવ ખેતી હવામાન", "Punjabi" to "ਲਾਇਵ ਖੇਤੀ ਮੌਸਮ", "Bengali" to "লাইভ আবহাওয়া"
        ),
        "weather_desc" to mapOf(
            "English" to "Partly Cloudy • Favorable for Spraying",
            "Tamil" to "பகுதி மேகமூட்டம் • உரம் தெளிக்க உகந்தது",
            "Hindi" to "आंशिक रूप से बादल • छिड़काव के लिए अनुकूल",
            "Telugu" to "పాక్షికంగా మేఘావృతం • పిచికారీకి అనుకూలం",
            "Malayalam" to "ഭാഗികമായി മേഘാവൃതമാണ് • തളിക്കാൻ അനുയോജ്യം",
            "Marathi" to "अंशतः ढगाळ • फवारणीसाठी अनुकूल",
            "Kannada" to "ಭಾಗಶಃ ಮೋಡ • ಸಿಂಪಡಣೆಗೆ ಅನುಕೂಲ",
            "Gujarati" to "અંશતઃ વાદળછાયું • છંટકાવ માટે અનુકૂળ",
            "Punjabi" to "ਅੰਸ਼ਕ ਤੌਰ 'ਤੇ ਬੱਦਲਵਾਈ • ਛਿੜਕਾਅ ਲਈ ਅਨੁਕੂਲ",
            "Bengali" to "আংশিক মেঘলা • স্প্রে করার জন্য উপযুক্ত"
        ),
        "rain" to mapOf(
            "English" to "Rain", "Tamil" to "மழை", "Hindi" to "बारिश",
            "Telugu" to "వర్షం", "Malayalam" to "മഴ", "Marathi" to "पाऊस",
            "Kannada" to "ಮಳೆ", "Gujarati" to "વરસાદ", "Punjabi" to "ਮੀਂਹ", "Bengali" to "বৃষ্টি"
        ),
        "humidity" to mapOf(
            "English" to "Humidity", "Tamil" to "ஈரப்பதம்", "Hindi" to "नमी",
            "Telugu" to "తేమ", "Malayalam" to "ആർദ്രത", "Marathi" to "आर्द्रता",
            "Kannada" to "ತೇವಾಂಶ", "Gujarati" to "ભેજ", "Punjabi" to "ਨਮੀ", "Bengali" to "আর্দ্রতা"
        ),

        // Precision Advisory
        "precision_advisory" to mapOf(
            "English" to "Today's Precision Advisory",
            "Tamil" to "இன்றைய துல்லிய வேளாண் ஆலோசனை",
            "Hindi" to "आज की सटीक कृषि सलाह",
            "Telugu" to "ఈరోజు ఖచ్చితమైన సలహా",
            "Malayalam" to "ഇന്നത്തെ കൃത്യതാ ഉപദേശം",
            "Marathi" to "आजचा अचूक शेती सल्ला",
            "Kannada" to "ಇಂದಿನ ನಿಖರ ಸಲಹೆ",
            "Gujarati" to "આજની ચોક્કસ સલાહ",
            "Punjabi" to "ਅੱਜ ਦੀ ਸਟੀਕ ਸਲਾਹ",
            "Bengali" to "আজকের কৃষি পরামর্শ"
        ),
        "advisory_body" to mapOf(
            "English" to "Morning hours between 7 AM - 9 AM are ideal for Neem Oil sprays. High humidity boosts leaf absorption against early fungal spores.",
            "Tamil" to "காலை 7 முதல் 9 மணி வரை வேப்ப எண்ணெய் தெளிக்க உகந்த நேரம். அதிக ஈரப்பதம் இலை உறிஞ்சுதலை அதிகரிக்கும்.",
            "Hindi" to "सुबह 7 से 9 बजे के बीच नीम के तेल का छिड़काव सबसे उत्तम है। उच्च नमी पत्तों के अवशोषण को बढ़ाती है।",
            "Telugu" to "ఉదయం 7 నుండి 9 గంటల మధ్య వేప నూనె పిచికారీ చేయడానికి అనుకూల సమయం.",
            "Malayalam" to "രാവിലെ 7 മണിക്കും 9 മണിക്കും ഇടയിൽ വേപ്പെണ്ണ തളിക്കാൻ അനുയോജ്യമാണ്.",
            "Marathi" to "सकाळी ७ ते ९ दरम्यान कडुनिंबाच्या तेलाची फवारणी करणे उत्तम.",
            "Kannada" to "ಬೆಳಿಗ್ಗೆ 7 ರಿಂದ 9 ಗಂಟೆಯ ಅವಧಿಯಲ್ಲಿ ಬೇವಿನ ಎಣ್ಣೆ ಸಿಂಪಡಣೆ ಮಾಡುವುದು ಉತ್ತಮ.",
            "Gujarati" to "સવારે 7 થી 9 ની વચ્ચે લીમડાના તેલનો છંટકાવ કરવો શ્રેષ્ઠ છે.",
            "Punjabi" to "ਸਵੇਰੇ 7 ਤੋਂ 9 ਵਜੇ ਦੇ ਵਿਚਕਾਰ ਨਿੰਮ ਦੇ ਤੇਲ ਦਾ ਛਿੜਕਾਅ ਸਭ ਤੋਂ ਵਧੀਆ ਹੈ।",
            "Bengali" to "সকাল ৭টা থেকে ৯টার মধ্যে নিম তেল স্প্রে করা সবচেয়ে ভালো।"
        ),

        // Quick Actions & Tools
        "smart_tools" to mapOf(
            "English" to "Smart Agricultural Tools",
            "Tamil" to "ஸ்மார்ட் விவசாய கருவிகள்",
            "Hindi" to "स्मार्ट कृषि उपकरण",
            "Telugu" to "స్మార్ట్ వ్యవసాయ పరికరాలు",
            "Malayalam" to "സ്മാർട്ട് കാർഷിക ഉപകരണങ്ങൾ",
            "Marathi" to "स्मार्ट शेती साधने",
            "Kannada" to "ಸ್ಮಾರ್ಟ್ ಕೃಷಿ ಉಪಕರಣಗಳು",
            "Gujarati" to "સ્માર્ટ ખેતી સાધનો",
            "Punjabi" to "ਸਮਾਰਟ ਖੇਤੀਬਾੜੀ ਔਜ਼ਾਰ",
            "Bengali" to "স্মার্ট কৃষি সরঞ্জাম"
        ),
        "explore_all" to mapOf(
            "English" to "Explore All", "Tamil" to "அனைத்தையும் காண்க", "Hindi" to "सभी देखें",
            "Telugu" to "అన్నీ చూడండి", "Malayalam" to "എല്ലാം കാണുക", "Marathi" to "सर्व पहा",
            "Kannada" to "ಎಲ್ಲವನ್ನೂ ನೋಡಿ", "Gujarati" to "બધું જુઓ", "Punjabi" to "ਸਾਰੇ ਦੇਖੋ", "Bengali" to "সব দেখুন"
        ),

        // Tool Cards
        "title_scan" to mapOf(
            "English" to "AI Disease Scan", "Tamil" to "AI நோய் கண்டறிதல்", "Hindi" to "एआई बीमारी जांच",
            "Telugu" to "AI వ్యాధి స్కాన్", "Malayalam" to "AI രോഗനിർണ്ണയം", "Marathi" to "AI आजार तपासणी",
            "Kannada" to "AI ರೋಗ ತಪಾಸಣೆ", "Gujarati" to "AI રોગ સ્કેન", "Punjabi" to "AI ਬੀਮਾਰੀ ਸਕੈਨ", "Bengali" to "AI রোগ নির্ণয়"
        ),
        "sub_scan" to mapOf(
            "English" to "Instant leaf diagnostic", "Tamil" to "உடனடி இலை பரிசோதனை", "Hindi" to "तुरंत पत्ते की जांच",
            "Telugu" to "తక్షణ ఆకు పరీక్ష", "Malayalam" to "തൽക്ഷണ ഇല പരിശോധന", "Marathi" to "झटपट पानांची तपासणी",
            "Kannada" to "ತಕ್ಷಣದ ಎಲೆ ತಪಾಸಣೆ", "Gujarati" to "ત્વરિત પાંદડા નિદાન", "Punjabi" to "ਤੁਰੰਤ ਪੱਤੇ ਦੀ ਜਾਂਚ", "Bengali" to "তাৎক্ষণিক পাতা পরীক্ষা"
        ),

        "title_fields" to mapOf(
            "English" to "My Fields", "Tamil" to "எனது நிலங்கள்", "Hindi" to "मेरे खेत",
            "Telugu" to "నా పొలాలు", "Malayalam" to "എന്റെ പാടങ്ങൾ", "Marathi" to "माझी शेती",
            "Kannada" to "ನನ್ನ ಜಮೀನುಗಳು", "Gujarati" to "મારા ખેતરો", "Punjabi" to "ਮੇਰੇ ਖੇਤ", "Bengali" to "আমার জমি"
        ),
        "sub_fields" to mapOf(
            "English" to "Manage farm plots & crops", "Tamil" to "நிலம் மற்றும் பயிர் மேலாண்மை", "Hindi" to "खेत और फसल प्रबंधन",
            "Telugu" to "పొలం మరియు పంటల నిర్వహణ", "Malayalam" to "പാടങ്ങളും വിളകളും കൈകാര്യം ചെയ്യുക", "Marathi" to "शेत आणि पिकांचे व्यवस्थापन",
            "Kannada" to "ಜಮೀನು ಮತ್ತು ಬೆಳೆ ನಿರ್ವಹಣೆ", "Gujarati" to "ખેતર અને પાક સંચાલન", "Punjabi" to "ਖੇਤ ਅਤੇ ਫਸਲਾਂ ਦਾ ਪ੍ਰਬੰਧਨ", "Bengali" to "জমি ও ফসল ব্যবস্থাপনা"
        ),

        "title_yield" to mapOf(
            "English" to "Yield Predictor", "Tamil" to "மகசூல் கணிப்பு", "Hindi" to "उपज का अनुमान",
            "Telugu" to "దిగుబడి అంచనా", "Malayalam" to "വിളവ് പ്രവചനം", "Marathi" to "उत्पादन अंदाज",
            "Kannada" to "ಇಳುವರಿ ಅಂದಾಜು", "Gujarati" to "ઉપજ અનુમાન", "Punjabi" to "ਪੈਦਾਵਾਰ ਦਾ ਅੰਦਾਜ਼ਾ", "Bengali" to "ফলন পূর্বাভাস"
        ),
        "sub_yield" to mapOf(
            "English" to "ML Soil & Harvest Analytics", "Tamil" to "ML மண் & அறுவடை பகுப்பாய்வு", "Hindi" to "एमएल मिट्टी और फसल विश्लेषण",
            "Telugu" to "ML నేల మరియు దిగుబడి విశ్లేషణ", "Malayalam" to "ML മണ്ണും വിളവെടുപ്പ് വിശകലനവും", "Marathi" to "ML माती आणि पीक विश्लेषण",
            "Kannada" to "ML ಮಣ್ಣು ಮತ್ತು ಇಳುವರಿ ವಿಶ್ಲೇಷಣೆ", "Gujarati" to "ML જમીન અને લણણી વિશ્લેષણ", "Punjabi" to "ML ਮਿੱਟੀ ਅਤੇ ਫਸਲ ਵਿਸ਼ਲੇਸ਼ਣ", "Bengali" to "ML মাটি ও ফসল বিশ্লেষণ"
        ),

        "title_forum" to mapOf(
            "English" to "Community Forum", "Tamil" to "விவசாயிகள் மன்றம்", "Hindi" to "किसान चर्चा मंच",
            "Telugu" to "రైతుల వేదిక", "Malayalam" to "കർഷക കൂട്ടായ്മ", "Marathi" to "शेतकरी चर्चा मंच",
            "Kannada" to "ರೈತರ ವೇದಿಕೆ", "Gujarati" to "ખેડૂત મંચ", "Punjabi" to "ਕਿਸਾਨ ਚਰਚਾ ਮੰਚ", "Bengali" to "কৃষক মঞ্চ"
        ),
        "sub_forum" to mapOf(
            "English" to "Connect with expert farmers", "Tamil" to "நிபுணர் விவசாயிகளுடன் இணையுங்கள்", "Hindi" to "अनुभवी किसानों से जुड़ें",
            "Telugu" to "నిపుణులైన రైతులతో కనెక్ట్ అవ్వండి", "Malayalam" to "വിദഗ്ദ്ധ കർഷകരുമായി ബന്ധപ്പെടുക", "Marathi" to "तज्ज्ञ शेतकऱ्यांशी जोडा",
            "Kannada" to "ತಜ್ಞ ರೈತರೊಂದಿಗೆ ಸಂಪರ್ಕಿಸಿ", "Gujarati" to "નિષ્ણાત ખેડૂતો સાથે જોડાઓ", "Punjabi" to "ਮਾਹਰ ਕਿਸਾਨਾਂ ਨਾਲ ਜੁੜੋ", "Bengali" to "অভিজ্ঞ কৃষকদের সাথে যুক্ত হন"
        ),

        "title_kisan_ai" to mapOf(
            "English" to "Kisan AI Chat", "Tamil" to "கிசான் AI சாட்", "Hindi" to "किसान एआई चैट",
            "Telugu" to "కిసాన్ AI చాట్", "Malayalam" to "കിസാൻ AI ചാറ്റ്", "Marathi" to "किसान AI चॅट",
            "Kannada" to "ಕಿಸಾನ್ AI ಚಾಟ್", "Gujarati" to "કિસાન AI ચેટ", "Punjabi" to "ਕਿਸਾਨ AI ਚੈਟ", "Bengali" to "কিসান AI চ্যাট"
        ),
        "sub_kisan_ai" to mapOf(
            "English" to "Ask crop & fertilizer queries", "Tamil" to "பயிர் & உரம் சந்தேகங்கள் கேளுங்கள்", "Hindi" to "फसल और खाद सवाल पूछें",
            "Telugu" to "పంట మరియు ఎరువుల ప్రశ్నలు అడగండి", "Malayalam" to "വിളകളും വളങ്ങളും സംബന്ധിച്ച സംശയങ്ങൾ", "Marathi" to "पीक आणि खत प्रश्न विचारा",
            "Kannada" to "ಬೆಳೆ ಮತ್ತು ಗೊಬ್ಬರದ ಪ್ರಶ್ನೆಗಳನ್ನು ಕೇಳಿ", "Gujarati" to "પાક અને ખાતર પ્રશ્નો પૂછો", "Punjabi" to "ਫਸਲ ਅਤੇ ਖਾਦ ਦੇ ਸਵਾਲ ਪੁੱਛੋ", "Bengali" to "ফসল ও সার সংক্রান্ত প্রশ্ন জিজ্ঞাসা করুন"
        ),

        "title_market" to mapOf(
            "English" to "Mandi Market Rates", "Tamil" to "சந்தை விலை நிலவரம்", "Hindi" to "मंडी भाव",
            "Telugu" to "మండీ మార్కెట్ ధరలు", "Malayalam" to "മാർക്കറ്റ് വിലകൾ", "Marathi" to "मंडी भाव",
            "Kannada" to "ಮಾರುಕಟ್ಟೆ ದರಗಳು", "Gujarati" to "મંડી ભાવ", "Punjabi" to "ਮੰਡੀ ਦੇ ਭਾਅ", "Bengali" to "মন্ডি দর"
        ),
        "sub_market" to mapOf(
            "English" to "Live daily crop commodity rates", "Tamil" to "தினசரி சந்தை விலை விவரங்கள்", "Hindi" to "दैनिक ताजा मंडी भाव",
            "Telugu" to "రోజువారీ తాజా పంట ధరలు", "Malayalam" to "ദിനപത്ര കാർഷിക വിലകൾ", "Marathi" to "दैनंदिन ताजे पीक भाव",
            "Kannada" to "ದೈನಂದಿನ ಬೆಳೆ ದರಗಳು", "Gujarati" to "દૈનિક તાજા પાક ભાવ", "Punjabi" to "ਰੋਜ਼ਾਨਾ ਤਾਜ਼ਾ ਮੰਡੀ ਭਾਅ", "Bengali" to "দৈনিক তাজা বাজার দর"
        ),

        "title_schemes" to mapOf(
            "English" to "Govt Schemes", "Tamil" to "அரசு திட்டங்கள்", "Hindi" to "सरकारी योजनाएं",
            "Telugu" to "ప్రభుత్వ పథకాలు", "Malayalam" to "സർക്കാർ പദ്ധതികൾ", "Marathi" to "शासकीय योजना",
            "Kannada" to "ಸರ್ಕಾರಿ ಯೋಜನೆಗಳು", "Gujarati" to "સરકારી યોજનાઓ", "Punjabi" to "ਸਰਕਾਰੀ ਯੋਜਨਾਵਾਂ", "Bengali" to "সরকারি প্রকল্প"
        ),
        "sub_schemes" to mapOf(
            "English" to "Agricultural Subsidies & Loans", "Tamil" to "விவசாய மானியங்கள் & கடன்கள்", "Hindi" to "कृषि सब्सिडी और लोन",
            "Telugu" to "వ్యవసాయ సబ్సిడీలు మరియు రుణాలు", "Malayalam" to "കാർഷിക സബ്‌സിഡികളും വായ്പകളും", "Marathi" to "शेती अनुदान आणि कर्ज",
            "Kannada" to "ಕೃಷಿ ಸಬ್ಸಿಡಿ ಮತ್ತು ಸಾಲಗಳು", "Gujarati" to "ખેતી સબસીડી અને લોન", "Punjabi" to "ਖੇਤੀਬਾੜੀ ਸਬਸਿਡੀਆਂ ਅਤੇ ਕਰਜ਼ੇ", "Bengali" to "কৃষি ভর্তুকি ও ঋণ"
        ),

        "title_profile" to mapOf(
            "English" to "Farm Profile", "Tamil" to "பண்ணை சுயவிவரம்", "Hindi" to "फार्म प्रोफाइल",
            "Telugu" to "ఫామ్ ప్రొఫైల్", "Malayalam" to "ഫാം പ്രൊഫൈൽ", "Marathi" to "फार्म प्रोफाइल",
            "Kannada" to "ಫಾರ್ಮ್ ಪ್ರೊಫೈಲ್", "Gujarati" to "ફાર્મ પ્રોફાઇલ", "Punjabi" to "ਫਾਰਮ ਪ੍ਰੋਫਾਈਲ", "Bengali" to "ফার্ম প্রোফাইল"
        ),
        "sub_profile" to mapOf(
            "English" to "Settings & Regional Language", "Tamil" to "அமைப்புகள் & வட்டார மொழி", "Hindi" to "सेटिंग्स और प्रांतीय भाषा",
            "Telugu" to "సెట్టింగ్‌లు & ప్రాంతీయ భాష", "Malayalam" to "ക്രമീകരണങ്ങളും പ്രാദേശിക ഭാഷയും", "Marathi" to "सेटिंग्ज आणि प्रादेशिक भाषा",
            "Kannada" to "ಸೆಟ್ಟಿಂಗ್‌ಗಳು ಮತ್ತು ಪ್ರಾದೇಶಿಕ ಭಾಷೆ", "Gujarati" to "સેટિંગ્સ અને પ્રાદેશિક ભાષા", "Punjabi" to "ਸੈਟਿੰਗਾਂ ਅਤੇ ਖੇਤਰੀ ਭਾਸ਼ਾ", "Bengali" to "সেটিংস ও আঞ্চলিক ভাষা"
        ),

        // User Profile Screen
        "profile_heading" to mapOf(
            "English" to "Farmer Profile & Settings",
            "Tamil" to "விவசாயி சுயவிவரம் & அமைப்புகள்",
            "Hindi" to "किसान प्रोफाइल और सेटिंग्स",
            "Telugu" to "రైతు ప్రొఫైల్ & సెట్టింగ్‌లు",
            "Malayalam" to "കർഷക പ്രൊഫൈലും ക്രമീകരണങ്ങളും",
            "Marathi" to "शेतकरी प्रोफाइल आणि सेटिंग्ज",
            "Kannada" to "ರೈತ ಪ್ರೊಫೈಲ್ ಮತ್ತು ಸೆಟ್ಟಿಂಗ್‌ಗಳು",
            "Gujarati" to "ખેડૂત પ્રોફાઇલ અને સેટિંગ્સ",
            "Punjabi" to "ਕਿਸਾਨ ਪ੍ਰੋਫਾਈਲ ਅਤੇ ਸੈਟਿੰਗਾਂ",
            "Bengali" to "কৃষক প্রোফাইল ও সেটিংস"
        ),
        "change_lang_btn" to mapOf(
            "English" to "Change Regional Language",
            "Tamil" to "வட்டார மொழியை மாற்று",
            "Hindi" to "क्षेत्रीय भाषा बदलें",
            "Telugu" to "ప్రాంతీయ భాషను మార్చండి",
            "Malayalam" to "പ്രാദേശിക ഭാഷ മാറ്റുക",
            "Marathi" to "प्रादेशिक भाषा बदला",
            "Kannada" to "ಪ್ರಾದೇಶಿಕ ಭಾಷೆಯನ್ನು ಬದಲಾಯಿಸಿ",
            "Gujarati" to "પ્રાદેશિક ભાષા બદલો",
            "Punjabi" to "ਖੇਤਰੀ ਭਾਸ਼ਾ ਬਦਲੋ",
            "Bengali" to "আঞ্চলিক ভাষা পরিবর্তন করুন"
        ),
        "logout_btn" to mapOf(
            "English" to "Logout Account",
            "Tamil" to "கணக்கிலிருந்து வெளியேறு",
            "Hindi" to "लॉगआउट करें",
            "Telugu" to "లాగ్అవుట్ చేయండి",
            "Malayalam" to "ലോഗ്ഔട്ട് ചെയ്യുക",
            "Marathi" to "लॉगआउट करा",
            "Kannada" to "ಲಾಗಿನ್‌ನಿಂದ ನಿರ್ಗಮಿಸಿ",
            "Gujarati" to "લોગ આઉટ કરો",
            "Punjabi" to "ਲੌਗਆਊਟ ਕਰੋ",
            "Bengali" to "লগআউট করুন"
        )
    )

    fun getString(key: String, language: String): String {
        val keyMap = translations[key] ?: return key
        return keyMap[language] ?: keyMap["English"] ?: key
    }
}
