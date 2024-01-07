import fs from 'fs'

class Test{
    async temperatureFromDevice(req, res){//получение температуры с телефона
        try{
            const {temperature} = req.params
                        
            fs.writeFileSync("choice_temperature.txt", temperature)
            console.log(temperature)
            return res.json({ message: true })
            
        }
        catch(e){
            console.log(e)
        }
    }

    async temperatureOnRaspberrypi(req, res){//отправка темперуты на расбери
        try{
                                    
            const temperature = fs.readFileSync('./choice_temperature.txt',
                { encoding: 'utf8', flag: 'r' });
            console.log(temperature)
            return res.json({ message: temperature })
            
        }
        catch(e){
            console.log(e)
        }
    }

    async temperatureFromRaspberrypi(req, res){//получение акутальной температуры с расбери
        try{
            const{temperature} = req.params

            fs.writeFileSync("actual_temperature.txt", temperature)
            console.log(temperature)
            return res.json({ message: true })
        }
        catch(e){
            console.log(e)
        }
    }

    async temperatureFromRaspberrypiOnDevice(req, res){//отправление актуальной температуры с расбери на телефон
        try{

            const temperature = fs.readFileSync('./actual_temperature.txt',
                { encoding: 'utf8', flag: 'r' });
            console.log(temperature)
            return res.json({ message: temperature })
        }
        catch(e){
            console.log(e)
        }
    }
}

export default new Test()