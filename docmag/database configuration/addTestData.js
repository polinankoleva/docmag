db.getSiblingDB("docmag").dropDatabase();
var database = db.getSiblingDB("docmag");
addTeachers(database);
addThesisDefences(database);

function addTeachers(database) {

  var userCollection = database.getCollection("users");

  var algebra = {
    Algebra: [
      ["Евгения", "Димитрова", "Великова", "доц. д-р", "velikova@fmi.uni-sofia.bg"],
      ["Недялко", "Димов", "Ненов", "проф. д.м.н", "nenov@fmi.uni-sofia.bg"],
      ["Азнив", "Киркор", "Каспарян", "проф д-р", "kasparia@fmi.uni-sofia.bg"],
      ["Пламен", "Николов", "Сидеров", "доц. д-р", "siderov@fmi.uni-sofia.bg"],
      ["Борис", "Василев", "Коцев", "гл. ас. д-р", "bkotzev@fmi.uni-sofia.bg"],
      ["Диана", "Илиева", "Радкова", "гл. ас. д-р", "dradkova@fmi.uni-sofia.bg"],
      ["Екатерина", "Петрова", "Михайлова", "гл. ас. д-р", "katiamih@fmi.uni-sofia.bg"],
      ["Мая", "Митева", "Стоянова", "доц. д-р", "stoyanova@fmi.uni-sofia.bg"],
      ["Николай","Пангелов","Колев","гл. ас. д-р", "nickyxy@fmi.uni-sofia.bg"],
      ["Николай", "Владимиров", "Владов","гл. ас. д-р", "vladov@fmi.uni-sofia.bg"],
      ["Асен", "Иванов", "Божилов", "гл. ас", "bojilov@fmi.uni-sofia.bg"],
      ["Татяна", "Любенова", "Тодорова", "гл. ас.", "tlt@fmi.uni-sofia.bg"],
      ["Дияна", "Василева", "Левченко", "гл. ас. д-р", "dianal@fmi.uni-sofia.bg"],
      ["Константин", "Драганов", "Табаков", "ас.", "ktabakov@fmi.uni-sofia.bg"]
    ]
  }

  var mechanics = {
    AnalyticalMechanics: [
      ["Тодор", "Асенов", "Парталин", "доц. д-р", "topart@fmi.uni-sofia.bg"],
      ["Любомир", "Кръстев", "Лолов", "проф. д.м.н", "lilov@fmi.uni-sofia.bg"],
      ["Богдана", "Атанасова", "Георгиева", "гл. ас. д-р", "georgieva@fmi.uni-sofia.bg"],
      ["Слави", "Лазаров", "Лазаров", "гл. ас.", "slazarov@fmi.uni-sofia.bg"],
      ["Цоло", "Петров", "Иванов", "проф. д.м.н", "tsoloiv@fmi.uni-sofia.bg"],
      ["Иван", "Димов", "Бозгуганов", "гл. ас.", "bozdugan@fmi.uni-sofia.bg"],
      ["Георги", "Кирилов", "Димитров", "математик", "georgikd@fmi.uni-sofia.bg"]
      // ["Андрей", "Константинов", "Сариев", "математик", ""]
    ]
  }

  var vois = {
    Statistics: [
      ["Маруся", "Никифорова", "Божкова", "доц. д-р", "ojkova@fmi.uni-sofia.bg"],
      ["Михаил", "Иванов", "Кръстанов", "проф. д.м.н", "krastanov @fmi.uni-sofia.bg"],
      ["Никола", "Иванов", "Янев", "проф. д-р", "choby@fmi.uni-sofia.bg"],
      ["Весела", "Кирилова", "Стоименова", "доц. д-р", "stoimenova@fmi.uni-sofia.bg"],
      ["Дончо", "Стефанов", "Дончев", "доц. д-р", "doncho@fmi.uni-sofia.bg"],
      ["Йордан", "Георгиев", "Митев", "доц. д-р", "jormit@fmi.uni-sofia.bg"],
      ["Леда", "Димитрова", "Минкова", "доц. д.м.н", "leda@fmi.uni-sofia.bg"],
      ["Надя", "Пейчева", "Златева", "доц. д-р", "zlateva@fmi.uni-sofia.bg"],
      ["Пламен", "Стоянов", "Матеев", "доц. д-р", "pmat@fmi.uni-sofia.bg"],
      ["Анна", "Александрова", "Попова", "гл. ас. д-р", "annap@fmi.uni-sofia.bg"],
      ["Мая", "Проданова", "Желязкова", "гл. ас. д-р", "zhelyazkova@fmi.uni-sofia.bg"],
      ["Венелин", "Георгиев", "Черногоров", "гл. ас.", "wily@fmi.uni-sofia.bg"],
      ["Емил", "Петков", "Каменов", "гл. ас.", "kamenov@fmi.uni-sofia.bg"],
      ["Нина", "Руменова", "Даскалова", "ас. д-р", "ninad@fmi.uni-sofia.bg"],
      ["Деница", "Парашкевова", "Григорова", "ас.", "dgrigorova@fmi.uni-sofia.bg"]
    ]
  }

  var geometry = {
    Geometry: [
      ["Стефан", "Петров", "Иванов", "проф. д.м.н", "ivanovsp@fmi.uni-sofia.bg"],
      ["Ася", "Петрова", "Русева", "доц. д-р", "assia@fmi.uni-sofia.bg"],
      ["Богдан", "Тодоров", "Александров", "доц. д-р", "btaleksand@fmi.uni-sofia.bg"],
      ["Веселка", "Маринова", "Михова", "доц. д-р", "mihova@fmi.uni-sofia.bg"],
      ["Гергана", "Великова", "Енева", "доц. д-р", "eneva@fmi.uni-sofia.bg"],
      ["Стефана", "Трифонова", "Хинева", "доц. д-р", "hinevast@fmi.uni-sofia.bg"],
      ["Симеон", "Петров", "Замковой", "доц. д-р", "zamkovoy@fmi.uni-sofia.bg"],
      ["Чавдар", "Георгиев", "Лозанов", "доц. д-р", "lozanov@fmi.uni-sofia.bg"],
      ["Иван", "Минчев", "Минчев", "доц. д-р", "minchev@fmi.uni-sofia.bg"],
      ["Маргарита", "Георгиева", "Спирова", "гл. ас. д-р", "spirova@fmi.uni-sofia.bg"],
      ["Юлиан", "Цанков", "Цанков", "гл. ас.", "ucankov@fmi.uni-sofia.bg"],
      ["Яна", "Алексиева", "Алексиева", "гл. ас.", "yana_a_n@fmi.uni-sofia.bg"],
      ["Невяна", "Димитрова", "Георгиева", "математик", "nevyanag@fmi.uni-sofia.bg"],
      ["Владимир", "", "Митанкин", "математик", "vladimirm@fmi.uni-sofia.bg"]
    ]
  }

  var differentialEquations = {
    DifferentialEquations: [
      ["Ангел", "Иванов", "Живков", "доц. д-р", "zhivkov@fmi.uni-sofia.bg"],
      ["Емил", "Иванов", "Хорозов", "чл. кор.", "horozov@fmi.uni-sofia.bg"],
      ["Недю", "Иванов", "Попиванов", "проф. д.м.н", "nedyu@fmi.uni-sofia.bg"],
      ["Геню", "Даков", "Дачев", "доц. д-р", "gdachev@fmi.uni-sofia.bg"],
      ["Йордан", "Велинов", "Йорданов", "доц. д-р", "iordanov@fmi.uni-sofia.bg"],
      ["Мария", "Георгиева", "Каратопраклиева", "доц. д-р", "ivmarkar@fmi.uni-sofia.bg"],
      ["Огнян", "Борисов", "Христов", "доц. д-р", "christov@fmi.uni-sofia.bg"],
      ["Тошко", "Борисов", "Боев", "доц. д-р", "boev@fmi.uni-sofia.bg"],
      // ["Светлин", "Георгиев", "Георгиев", "гл. ас. д-р", ""],
      ["Тодор", "Павлов", "Попов", "гл. ас. д-р", "topopover@fmi.uni-sofia.bg"],
      ["Цветан", "Димитров", "Христов", "гл. ас. д-р", "tsvetan@fmi.uni-sofia.bg"],
      ["Цветана", "Любенова", "Стоянова", "гл. ас. д-р", "cveti@fmi.uni-sofia.bg"]
    ]
  }

  var computingSystems = {
    ComputingSystems: [
      ["Стефан", "Станчев", "Димитров", "доц. д-р", "stefansd@fmi.uni-sofia.bg"],
      ["Антон", "Любенов", "Петков", "доц. д-р", "anton@fmi.uni-sofia.bg"],
      ["Атанас", "Иванов", "Терзиев", "доц. д-р", "aterziev@fmi.uni-sofia.bg"],
      ["Димитър", "Йорданов", "Биров", "доц. д-р", "birov@fmi.uni-sofia.bg"],
      // ["Камен", "Боянов", "Спасов", "доц. д-р", ""],
      ["Красимир", "Неделчев", "Манев", "доц. д-р", "manev@fmi.uni-sofia.bg"],
      ["Моника", "Христова", "Филипова", "гл. ас. д-р", "moni@fmi.uni-sofia.bg"],
      ["Румяна", "Христова", "Антонова", "гл. ас. д-р", "rhantonova@uni-sofia.bg"],
      ["Емилия", "Кирилова", "Живкова", "гл. ас.", "zivkova@fmi.uni-sofia.bg"],
      ["Румяна", "Петрова", "Лесева", "гл. ас.", "leseva@fmi.uni-sofia.bg"],
      ["Сабина", "Стефанова", "Бочева", "гл. ас", "sabina@fmi.uni-sofia.bg"],
      ["Стела", "", "Русева", "гл. ас. д-р", "stela@fmi.uni-sofia.bg"],
      ["Христина", "Александрова", "Зашева", "гл. ас.", "griza@fmi.uni-sofia.bg"],
      // ["Минко", "Маринов", "Марков", "гл. ас.", ""],
      ["Георги", "Иванов", "Георгиев", "гл. ас. д-р", "skelet@fmi.uni-sofia.bg"],
      ["Мария", "Господинова", "Ганева", "математик", "ganeva@fmi.uni-sofia.bg"]
    ]
  }

  var informatics = {
    InformationTechnology: [
      ["Красен", "Стефанов", "Стефанов", "проф. д-р", "krassen@fmi.uni-sofia.bg"],
      ["Евгений", "Христов", "Кръстев", "доц. д-р", "eck@fmi.uni-sofia.bg"],
      ["Илиана", "Христова", "Николова", "доц. д-р", "iliana@fmi.uni-sofia.bg"],
      ["Павел", "Христов", "Бойчев", "доц. д-р", "boytchev@fmi.uni-sofia.bg"],
      ["Елиза", "Петрова", "Стефанова", "гл. ас. д-р", "eliza@fmi.uni-sofia.bg"],
      ["Емилия", "Свиленова", "Бранкова", "гл. ас. д-р", "emibr@fmi.uni-sofia.bg"],
      ["Евгения", "Петрова", "Ковачева", "гл. ас.", "epk@fmi.uni-sofia.bg"],
      ["Траян", "Славчев", "Илиев", "гл. ас.", "t_iliev@fmi.uni-sofia.bg"],
      ["Атанас", "Георгиев", "Георгиев", "ас.", "atanas@fmi.uni-sofia.bg"],
      ["Зорница", "Здравкова", "Якова", "ас.", "yakova@uni-sofia.bg"],
      // ["Корнелия", "Йорданова", "Тодорова", "ас.", ""],
      ["Милен", "Митков", "Чечев", "ас.", "milen.chechev@fmi.uni-sofia.bg"],
      // ["Пенчо", "", "Михнев", "ас.", ""],
      ["Теменужка", "Борисова", "Зафирова-Малчева", "ас.", "tzafirova@fmi.uni-sofia.bg"]
    ]
  }

  var complexAnalisys = {
    ComplexAnalysis: [
      ["Ваня", "Христов", "Хаджийски", "доц. д-р", "hadzijski@fmi.uni-sofia.bg"],
      ["Георги", "Добромиров", "Димов", "проф. д.м.н", "gdimov@fmi.uni-sofia.bg"],
      ["Евгений", "Христов", "Христов", "проф. д.м.н", "hristov@fmi.uni-sofia.bg"],
      ["Красимира", "Влъчкова", "Александрова", "гл. ас. д-р", "krassivl@fmi.uni-sofia.bg"],
      ["Руси", "Георгиев", "Йорданов", "гл. ас. д-р", "yordanov@fmi.uni-sofia.bg"],
      ["Александър", "Василев", "Александров", "гл. ас.", "ava@fmi.uni-sofia.bg"],
      ["Васил", "Станев", "Гочев", "гл. ас.", "v.gotchev@fmi.uni-sofia.bg"],
      ["Елза", "Петрова", "Иванова", "гл. ас.", "elza@fmi.uni-sofia.bg"]
    ]
  }

  var logic = {
    MathematicalLogic: [
      ["Александра", "Андреева", "Соскова", "доц. д-р", "asoskova@fmi.uni-sofia.bg"],
      ["Тинко", "Величков", "Тинчев", "проф. д-р", "tinko@fmi.uni-sofia.bg"],
      ["Димитър", "Иванов", "Вакарелов", "проф. д.м.н", "dvak@fmi.uni-sofia.bg"],
      ["Димитър", "Генчев", "Скордев", "проф. д.м.н.", "skordev@fmi.uni-sofia.bg"],
      ["Анатолий", "Олегович", "Буда", "доц. д-р", "buda@fmi.uni-sofia.bg"],
      ["Ангел", "Василев", "Дичев", "доц. д-р", "ditchev@fmi.uni-sofia.bg"],
      ["Мария", "Иванова", "Соскова", "доц. д-р", "msoskova@fmi.uni-sofia.bg"],
      ["Петьо", "Петров", "Петков", "доц. д-р", "ppetkov@fmi.uni-sofia.bg"],
      ["Стела", "Колева", "Николова", "доц. д-р", "stenik@fmi.uni-sofia.bg"],
      ["Христо", "Александров", "Ганчев", "доц. д-р", "h.ganchev@fmi.uni-sofia.bg"],
      // ["Антон", "Кирилов", "Зиновиев", "гл. ас.", "anton@lml.bas.bg"],
      ["Петър", "Николаев", "Митанкин", "гл. ас. д-р", "pmitankin@fmi.uni-sofia.bg"],
      ["Стефан", "Володов", "Вътев", "ас.", "stefanv@fmi.uni-sofia.bg"]
      // ["Стефан", "Владимиров", "Герджиков", "ас.", ""]
    ]
  }

  var education = {
    MathematicsEducatio: [
      ["Кирил", "Георгиев", "Банков", "проф. д-р", "kbankov@fmi.uni-sofia.bg"],
      ["Иван", "Костадинов", "Тонов", "проф. д-р", "tonov@fmi.uni-sofia.bg"],
      ["Ангел", "Илиев", "Ангелов", "доц. д-р", "aangelov@fmi.uni-sofia.bg"],
      ["Юлия", "Димитрова", "Нинова", "доц. д-р", "ninova@fmi.uni-sofia.bg"],
      ["Диана", "Петрова", "Раковска", "гл. ас.", "drakowska@fmi.uni-sofia.bg"],
      ["Николина", "Илиева", "Николова", "гл. ас.", "nnikolova@fmi.uni-sofia.bg"],
      // ["Ирина", "Здравкова", "Вутова", "гл. ас.", ""],
      ["Ридван", "Мустафов", "Исуфов", "гл. ас.", "ridvan@fmi.uni-sofia.bg"],
      ["Теодоси", "Асенов", "Витанов", "гл. ас.", "vitanov@fmi.uni-sofia.bg"],
      ["Жулгена", "Несим", "Бенбасат-Банкова", "д-р", "julgenaben@fmi.uni-sofia.bg"],
      ["Таня", "Георгиева", "Тонова", "математик", "ttonova@fmi.uni-sofia.bg"]
    ]
  }

  var softTechnologies = {
    SoftwareTechnologies: [
      ["Силвия", "Христова", "Илиева", "проф. д-р", "sylvia@fmi.uni-sofia.bg"],
      ["Христо", "Августов", "Кабакчиев", "проф. д.т.н", "ckabakchiev@fmi.uni-sofia.bg"],
      ["Боян", "Паскалев", "Бончев", "проф. д-р", "bbontchev@fmi.uni-sofia.bg"],
      ["Александър", "Тодоров", "Григоров", "доц. д-р", "alexander.grigorov@fmi.uni-sofia.bg"],
      ["Елисавета", "", "Гурова", "доц. д-р", "elis@fmi.uni-sofia.bg"], 
      ["Иван", "", "Койчев", "доц. д-р", "ivan.koychev@fmi.uni-sofia.bg"],
      ["Олга", "Илиева", "Георгиева", "доц. д-р", "o.georgieva@fmi.uni-sofia.bg"],
      ["Петко", "Русков", "Русков", "доц. д-р", "petkor@fmi.uni-sofia.bg"],
      ["Александър", "Димов", "Димов", "доц. д-р", "aldi@fmi.uni-sofia.bg"],
      ["Десислава", "Георгиева", "Петрова-Антонова", "доц. д-р", "d.petrova@fmi.uni-sofia.bg"],
      ["Милен", "Йорданов", "Петров", "доц. д-р", "milenp@fmi.uni-sofia.bg"],
      ["Георги", "Христов", "Къдрев", "ас.", "gkadrev@fmi.uni-sofia.bg"]
      // ,["Ирена", "Атанасова", "Павлова", "ас.", ""]
    ]
  }

  var numMethods = {
    NumericalMethods: [
      ["Татяна", "Параскевова", "Черногорова", "доц. д-р", "chernogorova@fmi.uni-sofia.bg"],
      ["Гено", "Петков", "Николов", "проф. д.м.н.", "geno@fmi.uni-sofia.bg"],
      ["Стефка", "Николаева", "Димова", "проф. д.м.н.", "dimova@fmi.uni-sofia.bg"],
      ["Веселин", "Андреев", "Гушев", "доц. д-р", "v_gushev@fmi.uni-sofia.bg"],
      ["Милен", "Божинов", "Милев", "доц. д-р", "milev@fmi.uni-sofia.bg"],
      ["Никола", "Георгиев", "Найденов", "доц. д-р", "nikola@fmi.uni-sofia.bg"],
      ["Петър", "Пейнов", "Петров", "гл. ас. д-р", "peynov@fmi.uni-sofia.bg"],
      ["Ангелина", "Иванова", "Георгиева", "гл. ас.", "yotova@fmi.uni-sofia.bg"],
      ["Иван", "Георгиев", "Христов", "гл. ас.", "ivanh@fmi.uni-sofia.bg"],
      ["Ана", "Александрова", "Авджиева", "ас.", "aavdzhieva@fmi.uni-sofia.bg"],
      ["Вая", "Теохари", "Ракидзи", "ас.", "rakidzi@fmi.uni-sofia.bg"]
    ]
  }

  var analisys = {
    Analysis: [
      ["Владимир", "", "Бабев", "доц. д-р", "babev@fmi.uni-sofia.bg"],
      ["Дойчин", "Иванов", "Толев", "проф. д.м.н.", "dtolev@fmi.uni-sofia.bg"],
      ["Рони", "Нисим", "Леви", "проф. д.м.н", "levy@fmi.uni-sofia.bg"],
      ["Румен", "Петров", "Малеев", "проф. д.м.н", "maleev@fmi.uni-sofia.bg"],
      ["Людмила", "Йорданова", "Николова", "проф. д-р", "ludmilan@fmi.uni-sofia.bg"],
      ["Борислав", "Радков", "Драганов", "доц. д-р", "bdraganov@fmi.uni-sofia.bg"],
      ["Васил", "Валдемаров", "Цанов", "доц. д-р", "tsanov@fmi.uni-sofia.bg"],
      ["Георги", "Атанасов", "Александров", "доц. д-р", "galex@fmi.uni-sofia.bg"],
      ["Милен", "Николаев", "Иванов", "доц. д-р", "milen@fmi.uni-sofia.bg"],
      ["Милко", "Дмянов", "Такев", "доц. д-р", "takev@fmi.uni-sofia.bg"],
      ["Надежда", "Костадинова", "Рибарска", "доц. д-р", "ribarska@fmi.uni-sofia.bg"],
      ["Първан", "Евтимов", "Първанов", "доц. д-р", "pparvan@fmi.uni-sofia.bg"],
      ["Владимир", "Атанасов", "Попов", "гл. ас.", "popov@fmi.uni-sofia.bg"],
      ["Иван", "Кирилов", "Делев", "гл. ас.", "delev@fmi.uni-sofia.bg"],
      ["Николай", "Петров", "Буюклиев", "гл. ас.", "bujuk@fmi.uni-sofia.bg"],
      ["Росен", "Асенов", "Николов", "гл. ас.", "rnikolov@fmi.uni-sofia.bg"],
      ["Евгени", "Петров", "Недялков", "математик", "nedialkov@fmi.uni-sofia.bg"]
    ]
  }

  var departments = [algebra, mechanics, vois, geometry, differentialEquations, computingSystems, 
  complexAnalisys, informatics, logic, education, softTechnologies, numMethods, analisys];

  for (var departmentIdx in departments) {
    for (var departmentName in departments[departmentIdx]) {
      for (var teacherIndex in departments[departmentIdx][departmentName]) {
        var teacherInfo = departments[departmentIdx][departmentName][teacherIndex];
        var teacherProfile = {
            firstName: teacherInfo[0], 
            surname: teacherInfo[1],
            lastName: teacherInfo[2], 
            email: teacherInfo[4], 
            faculty: "ФМИ",
            department: departmentName,
            degree: teacherInfo[3]
          }
        userCollection.insert({
            username:  teacherInfo[4].split("@")[0],
            type : "Teacher", 
            profile: teacherProfile
          });
      } 
    }
  }
}

function addThesisDefences(database) {
  var userCollection = database.getCollection("users");
  var thesisDefenceCollection = database.getCollection("thesisdefences");

  var teachers = database.users.find({"profile.department": "SoftwareTechnologies"}, {_id: 1}).toArray();

  var thesisDefenceId = ObjectId();
  var thesisDefence = {
    _id: thesisDefenceId,
    date: new Date(),
    commissionParticipantIds: userIds(teachers).slice(0, 3)
  }

  var thesisDefence2 = {
    date: new Date("February 09, 2014"),
    commissionParticipantIds: userIds(teachers).slice(3, 7)
  }

  thesisDefenceCollection.insert(thesisDefence);
  thesisDefenceCollection.insert(thesisDefence2);
}

function userIds(users) {
  var userIds = [];
  for (var userIndex in users) {
    userIds.push(users[userIndex]._id);
  }
  return userIds;
}