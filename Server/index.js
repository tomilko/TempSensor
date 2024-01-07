import Router from 'express'
import mainController from '../controller/mainController.js'

const router = new Router()

router.get('/device/:temperature', mainController.temperatureFromDevice)

router.get('/raspberrypi/:temperature', mainController.temperatureFromRaspberrypi)

router.get('/actual/raspberrypi/', mainController.temperatureFromRaspberrypiOnDevice)

router.get('/choice/raspberrypi/', mainController.temperatureOnRaspberrypi)


export default router