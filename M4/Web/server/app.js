const express = require('express');
const fs = require('fs');
const path = require('path');
const hbs = require('hbs');
const MySQL = require('./utilsMySQL');

const app = express();
const port = 3000;

// Detectar si estem al Proxmox (si és pm2)
const isProxmox = !!process.env.PM2_HOME;

// Iniciar connexió MySQL
const db = new MySQL();
if (!isProxmox) {
  db.init({
    host: '127.0.0.1',
    port: 3307,
    user: 'sacerdote',
    password: 'C1v1l1z4t10n',
    database: 'civilizations'
  });
} else {
  db.init({
    host: '127.0.0.1',
    port: 3306,
    user: 'sacerdote',
    password: 'C1v1l1z4t10n',
    database: 'civilizations'
  });
}

// Static files - ONLY ONCE
app.use(express.static('public'))
app.use(express.urlencoded({ extended: true }))

// Disable cache
app.use((req, res, next) => {
  res.setHeader('Cache-Control', 'no-store, no-cache, must-revalidate, proxy-revalidate');
  res.setHeader('Pragma', 'no-cache');
  res.setHeader('Expires', '0');
  res.setHeader('Surrogate-Control', 'no-store');
  next();
});

// Handlebars
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'hbs');

// Registrar "Helpers .hbs" aquí
hbs.registerHelper('eq', (a, b) => a == b);
hbs.registerHelper('gt', (a, b) => a > b);
hbs.registerHelper('formatNumber', (n) => {
  if (n === null || n === undefined) return '0';
  return Number(n).toLocaleString('en-US');
});

// Partials de Handlebars
hbs.registerPartials(path.join(__dirname, 'views', 'partials'));

// Route
/*app.get('/', async (req, res) => {
  try {
    // Obtenir les dades de la base de dades
    const cursosRows = await db.query('SELECT id, nom, tematica FROM cursos ORDER BY id');
    const especialitatsRows = await db.query('SELECT id, nom FROM especialitats ORDER BY nom');

    // Transformar les dades a JSON (per les plantilles .hbs)
    // Cal informar de les columnes i els seus tipus
    const cursosJson = db.table_to_json(cursosRows, { id: 'number', nom: 'string', tematica: 'string' });
    const especialitatsJson = db.table_to_json(especialitatsRows, { id: 'number', nom: 'string' });

    // Llegir l'arxiu .json amb dades comunes per a totes les pàgines
    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    );

    // Construir l'objecte de dades per a la plantilla
    const data = {
      cursos: cursosJson,
      especialitats: especialitatsJson,
      common: commonData
    };

    // Renderitzar la plantilla amb les dades
    res.render('index', data);
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});*/

app.get('/civilitzacio', async (req, res) => {
  try {
    const civRows = await db.query(`
      SELECT civilization_id, name, wood_amount, iron_amount, food_amount, mana_amount,
        magicTower_counter, church_counter, farm_counter, smithy_counter, carpentry_counter,
        technology_defense_level, technology_attack_level, battles_counter
      FROM civilization_stats LIMIT 1
    `);

    const civList = civRows && civRows.length > 0
      ? db.table_to_json(civRows, {
          civilization_id: 'number', name: 'string', wood_amount: 'number',
          iron_amount: 'number', food_amount: 'number', mana_amount: 'number',
          magicTower_counter: 'number', church_counter: 'number', farm_counter: 'number',
          smithy_counter: 'number', carpentry_counter: 'number',
          technology_defense_level: 'number', technology_attack_level: 'number',
          battles_counter: 'number'
        })
      : [];
    
    const civ = civList[0] || null;

    // 2. Obtener la cantidad de CADA tipo de unidad para esta civilización
    let unidadesAtaque = [];
    if (civ) {
      const unitsAtRows = await db.query(
        `SELECT type, COUNT(*) as cantidad 
         FROM attack_units_stats 
         WHERE civilization_id = ${civ.civilization_id} 
         GROUP BY type`
      );
    
          // Esto genera un array de objetos: [{type: 'Swordsman', cantidad: 14}, {type: 'Archer', cantidad: 5}]
      unidadesAtaque = db.table_to_json(unitsAtRows, { type: 'string', cantidad: 'number' });
    }

    let unidadesDefensa = [];
    if (civ) {
      const unitsDeRows = await db.query(
        `SELECT type, COUNT(*) as cantidad 
         FROM defense_units_stats 
         WHERE civilization_id = ${civ.civilization_id} 
         GROUP BY type`
      );
    
      unidadesDefensa = db.table_to_json(unitsDeRows, { type: 'string', cantidad: 'number' });
    }

    let unidadesEspecial = [];
    if (civ) {
      const unitsEsRows = await db.query(
        `SELECT type, COUNT(*) as cantidad 
         FROM special_units_stats 
         WHERE civilization_id = ${civ.civilization_id} 
         GROUP BY type`
      );
    
          // Esto genera un array de objetos: [{type: 'Swordsman', cantidad: 14}, {type: 'Archer', cantidad: 5}]
      unidadesEspecial = db.table_to_json(unitsEsRows, { type: 'string', cantidad: 'number' });
    }

    const commonData = JSON.parse(fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8'));
        // 3. Enviamos los datos a la vista
    res.render('civilitzacio', { 
      civ, 
      attack: unidadesAtaque, 
      defense: unidadesDefensa, 
      special: unidadesEspecial,
      common: commonData 
    });
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/programadors', (req, res) => {
  try{
            // Llegir l'arxiu .json amb dades comunes per a totes les pàgines
    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    );

    // Construir l'objecte de dades per a la plantilla
    const data = {
      common: commonData
    };
    res.render('programadors',data); 

  }catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/batalles', async (req, res) => {
  try {
    const battleRows = await db.query(`
      SELECT battle_id, civilization_id, num_battle, wood_acquired, iron_acquired
      FROM battle_stats
    `);

    const battles = db.table_to_json(battleRows, {
      battle_id: 'number', civilization_id: 'number', num_battle: 'number',
      wood_acquired: 'number', iron_acquired: 'number'
    });

    const commonData = JSON.parse(fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8'));
    
    res.render('batalles', { battles, total: battles.length, common: commonData });
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/', async (req, res) => {
  try {
    const battleRows = await db.query(`
      SELECT battle_id, civilization_id, num_battle, wood_acquired, iron_acquired
      FROM battle_stats
      ORDER BY battle_id DESC
      LIMIT 2
    `);

    const battles = db.table_to_json(battleRows, {
      battle_id: 'number', civilization_id: 'number', num_battle: 'number',
      wood_acquired: 'number', iron_acquired: 'number'
    });

    const commonData = JSON.parse(fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8'));
    res.render('index', { battles, common: commonData });
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/informes', async (req, res) => {
  try {
    const battleId = parseInt(req.query.informe, 10);
    if (!Number.isInteger(battleId) || battleId <= 0) {
      return res.redirect('/batalles');
    }

    const battleRows = await db.query(`
      SELECT battle_id, civilization_id, num_battle, wood_acquired, iron_acquired
      FROM battle_stats WHERE battle_id = ${battleId} LIMIT 1
    `);

    if (!battleRows || battleRows.length === 0) {
      return res.status(404).send('Batalla no trobada');
    }



    const battle = db.table_to_json(battleRows, {
      battle_id: 'number', civilization_id: 'number', num_battle: 'number',
      wood_acquired: 'number', iron_acquired: 'number'
    })[0];

    const logRows = await db.query(`
      SELECT bl.civilization_id, bl.num_battle, bl.num_line, bl.log_entry
      FROM battle_log bl
      JOIN battle_stats bs ON bs.civilization_id = bl.civilization_id AND bs.num_battle = bl.num_battle
      WHERE bs.battle_id = ${battleId}
      ORDER BY bl.num_line ASC
    `);

    const logs = db.table_to_json(logRows, {
      num_line: 'number', log_entry: 'string'
    });

    const logsProcesados = logs.map(log => {
    if (log.log_entry) {
      log.log_entry = log.log_entry.replace(/\n/g, '<br>');
    }
    return log;
    });

    /*if (logs.log_entry) {
      logs.log_entry = logs.log_entry.replace(/\n/g, '<br>');
    }*/

    const commonData = JSON.parse(fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8'));
    res.render('informes', { battle, logs: logsProcesados, common: commonData });
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/cursos', async (req, res) => {
  try {

    // Obtenir les dades de la base de dades
    const cursosRows = await db.query(`
      SELECT
        c.id,
        c.nom,
        c.tematica,
        COALESCE(
          GROUP_CONCAT(DISTINCT m.nom ORDER BY m.nom SEPARATOR ', '),
          '—'
        ) AS mestre_nom
      FROM cursos c
      LEFT JOIN mestre_curs mc ON mc.curs_id = c.id
      LEFT JOIN mestres m ON m.id = mc.mestre_id
      GROUP BY c.id, c.nom, c.tematica
      ORDER BY c.id;
    `);

    // Transformar les dades a JSON (per les plantilles .hbs)
    const cursosJson = db.table_to_json(cursosRows, {
      id: 'number',
      nom: 'string',
      tematica: 'string',
      mestre_nom: 'string'
    });

    // Llegir l'arxiu .json amb dades comunes per a totes les pàgines
    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    );

    // Construir l'objecte de dades per a la plantilla
    const data = {
      cursos: cursosJson,
      common: commonData
    };

    // Renderitzar la plantilla amb les dades
    res.render('cursos', data);
  } catch (err) {
    console.error(err);
    res.status(500).send('Error consultant la base de dades');
  }
});

app.get('/curs', async (req, res) => {
  try {
    // Llegit el valor del paràmetre "id" en format enter
    const cursId = parseInt(req.query.id, 10)

    // Validar que és un número enter positiu (o respondre amb error 400)
    if (!Number.isInteger(cursId) || cursId <= 0) {
      return res.status(400).send('Paràmetre id invàlid')
    }

    // Query only the requested course
    const cursosRows = await db.query(`
      SELECT
        c.id,
        c.nom,
        c.tematica,
        COALESCE(
          GROUP_CONCAT(DISTINCT m.nom ORDER BY m.nom SEPARATOR ', '),
          '—'
        ) AS mestre_nom
      FROM cursos c
      LEFT JOIN mestre_curs mc ON mc.curs_id = c.id
      LEFT JOIN mestres m ON m.id = mc.mestre_id
      WHERE c.id = ${[cursId]}
      GROUP BY c.id, c.nom, c.tematica
      LIMIT 1;`)

    // Si no s'ha trobat cap curs amb aquest id, respondre amb error 404
    if (!cursosRows || cursosRows.length === 0) {
      return res.status(404).send('Curs no trobat')
    }

    // Transformar les dades a JSON (per les plantilles .hbs)
    const cursosJson = db.table_to_json(cursosRows, {
      id: 'number',
      nom: 'string',
      tematica: 'string',
      mestre_nom: 'string'
    })

    // Llegir l'arxiu .json amb dades comunes per a totes les pàgines
    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    )

    // Construir l'objecte de dades per a la plantilla
    // com que tenim una llista amb un sol element, agafem directament el primer element (cursosJson[0])
    const data = {
      curs: cursosJson[0],
      common: commonData
    }

    // Render a new template (recommended)
    res.render('curs', data)
  } catch (err) {
    console.error(err)
    res.status(500).send('Error consultant la base de dades')
  }
})

app.get('/cursAdd', async (req, res) => {
  try {
    
    // Llegir l'arxiu .json amb dades comunes per a totes les pàgines
    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    )

    // Construir l'objecte de dades per a la plantilla
    // com que tenim una llista amb un sol element, agafem directament el primer element (cursosJson[0])
    const data = {
      common: commonData
    }

    // Render a new template (recommended)
    res.render('cursAdd', data)
  } catch (err) {
    console.error(err)
    res.status(500).send('Error consultant la base de dades')
  }
})

app.get('/cursEdit', async (req, res) => {
  try {
    const cursId = parseInt(req.query.id, 10)

    if (!Number.isInteger(cursId) || cursId <= 0) {
      return res.status(400).send('Paràmetre id invàlid')
    }

    const cursRows = await db.query(`
      SELECT
        c.id,
        c.nom,
        c.tematica,
        MIN(m.id) AS mestre_id
      FROM cursos c
      LEFT JOIN mestre_curs mc ON mc.curs_id = c.id
      LEFT JOIN mestres m ON m.id = mc.mestre_id
      WHERE c.id = ${cursId}
      GROUP BY c.id, c.nom, c.tematica
      LIMIT 1;
    `)

    if (!cursRows || cursRows.length === 0) {
      return res.status(404).send('Curs no trobat')
    }

    const mestresRows = await db.query(`SELECT id, nom FROM mestres ORDER BY nom;`)

    const cursJson = db.table_to_json(cursRows, {
      id: 'number',
      nom: 'string',
      tematica: 'string',
      mestre_id: 'number'
    })[0]

    const mestresJson = db.table_to_json(mestresRows, { id: 'number', nom: 'string' })

    const commonData = JSON.parse(
      fs.readFileSync(path.join(__dirname, 'data', 'common.json'), 'utf8')
    )

    res.render('cursEdit', {
      curs: cursJson,
      mestres: mestresJson,
      common: commonData
    })
  } catch (err) {
    console.error(err)
    res.status(500).send('Error consultant la base de dades')
  }
})

app.post('/create', async (req, res) => {
  try {

    const table = req.body.table

    if (table == "cursos") {

      const nom = req.body.nom
      const tematica = req.body.tematica

      // Basic validation
      if (!nom || !tematica) {
        return res.status(400).send('Falten dades')
      }

      await db.query(
        `
        INSERT INTO cursos (nom, tematica)
        VALUES ("${nom}", "${tematica}")
        `
      )

      // Redirect to list of courses
      res.redirect('/cursos')
    }

  } catch (err) {
    console.error(err)
    res.status(500).send('Error afegint el curs')
  }
})

app.post('/delete', async (req, res) => {
  try {

    const table = req.body.table

    if (table == "cursos") {

      const id = parseInt(req.body.id, 10)

      // Basic validation
      if (!Number.isInteger(id) || id <= 0) {
        return res.status(400).send('ID de curs invàlid')
      }

      await db.query(
        `DELETE FROM cursos WHERE id = ${id}`
      )

      res.redirect('/cursos')
    }

  } catch (err) {
    console.error(err)
    res.status(500).send('Error esborrant el curs')
  }
})

app.post('/update', async (req, res) => {
  try {

    const table = req.body.table

    if (table == "cursos") {

      const id = parseInt(req.body.id, 10)
      const mestre_id = parseInt(req.body.mestre_id, 10)
      const nom = req.body.nom
      const tematica = req.body.tematica

      // Basic validation
      if (!Number.isInteger(id) || id <= 0) return res.status(400).send('ID invàlid')
      if (!Number.isInteger(mestre_id) || mestre_id <= 0) return res.status(400).send('Mestre invàlid')
      if (!nom || !tematica) return res.status(400).send('Falten dades')

      // Update curs
      await db.query(`
        UPDATE cursos
        SET nom = "${nom}", tematica = "${tematica}"
        WHERE id = ${id};
      `)

      // Keep only 1 mestre per curs (UI)
      await db.query(`DELETE FROM mestre_curs WHERE curs_id = ${id};`)
      await db.query(`INSERT INTO mestre_curs (mestre_id, curs_id) VALUES (${mestre_id}, ${id});`)

      res.redirect(`/curs?id=${id}`)
    }
  } catch (err) {
    console.error(err)
    res.status(500).send('Error editant el curs')
  }
})

// Start server
const httpServer = app.listen(port, () => {
  console.log(`http://localhost:${port}`);
});

// Graceful shutdown
process.on('SIGINT', async () => {
  await db.end();
  httpServer.close();
  process.exit(0);
});