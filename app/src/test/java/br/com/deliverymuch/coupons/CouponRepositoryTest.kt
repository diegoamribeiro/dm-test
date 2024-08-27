package br.com.deliverymuch.coupons

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CouponRepositoryTest {

    private lateinit var context: Context
    private lateinit var repository: CouponRepository

    @Before
    fun setup() {
        context = mockk<Context>()
        repository = CouponRepository(context)
    }

    @Test
    fun `loadCoupons should return a non-null list of coupons`() {
        val resources = mockk<android.content.res.Resources>()
        every { context.resources } returns (resources)

        val inputStream = this::class.java.classLoader?.getResourceAsStream("coupons.json")
        every {
            resources.openRawResource(
                any()
            )
        } returns inputStream!!

        val coupons = repository.loadCupons()

        assertNotNull(coupons)
    }

    @Test
    fun `loadCoupons should return a list with the correct number of coupons`() {
        val resources = mockk<android.content.res.Resources>()
        every { context.resources } returns (resources)

        val inputStream = this::class.java.classLoader?.getResourceAsStream("coupons.json")
        every {
            resources.openRawResource(
                any()
            )
        } returns inputStream!!

        val coupons = repository.loadCupons()

        assertEquals(2, coupons?.size)
    }
}