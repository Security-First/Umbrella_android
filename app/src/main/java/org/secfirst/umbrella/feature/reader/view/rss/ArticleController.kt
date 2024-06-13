package org.secfirst.umbrella.feature.reader.view.rss

import android.os.Bundle
import android.util.Patterns
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.einmalfel.earl.Item
import kotlinx.android.synthetic.main.article_view.*
import org.secfirst.umbrella.R
import org.secfirst.umbrella.component.WebViewController
import org.secfirst.umbrella.data.database.reader.RSS
import org.secfirst.umbrella.feature.base.view.BaseController

class ArticleController(bundle: Bundle) : BaseController(bundle) {

    private val onClickOpenArticle: (Item) -> Unit = this::onClickLearnMore
    private val rss by lazy { args.getSerializable(EXTRA_RSS) as RSS }

    constructor(rss: RSS) : this(Bundle().apply {
        putSerializable(EXTRA_RSS, rss)
    })

    companion object {
        const val EXTRA_RSS = "rss_selected"
    }

    override fun onInject() {

    }

    override fun onAttach(view: View) {
        iniRecycle()
        enableNavigation(false)
        setUpToolbar()
        openCardListAdapter()
        super.onAttach(view)
    }

    override fun onDestroyView(view: View) {
        enableNavigation(true)
        super.onDestroyView(view)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.article_view, container, false)
    }

    private fun openCardListAdapter() {
        val articleAdapter = ArticleCardAdapter(onClickOpenArticle)
        articleAdapter.addAll(rss)
        recyclerViewArticle?.let { it.adapter = articleAdapter }
    }

    private fun openSimpleListAdapter() {
        val articleAdapter = ArticleSimpleAdapter(onClickOpenArticle)
        articleAdapter.addAll(rss)
        recyclerViewArticle?.let { it.adapter = articleAdapter }
    }

    private fun iniRecycle() {
        val layoutManager = LinearLayoutManager(activity)
        val itemDecor = DividerItemDecoration(activity, layoutManager.orientation)
        recyclerViewArticle?.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.removeItemDecoration(itemDecor)
        }
    }

    private fun onClickLearnMore(item: Item) {
        if (item.link != null && Patterns.WEB_URL.matcher(item.link).matches()) {
            router.pushController(RouterTransaction.with(WebViewController(item.link!!)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.article_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_card_list -> setUplMenuItem(item)
        }
        return true
    }

    private fun setUplMenuItem(menuItem: MenuItem) {
        if (menuItem.title == context.getString(R.string.article_simple_list)) {
            menuItem.title = context.getString(R.string.card_list)
            openSimpleListAdapter()
        } else {
            menuItem.title = context.getString(R.string.article_simple_list)
            openCardListAdapter()
        }
    }


    private fun setUpToolbar() {
        articleToolbar?.let {
            it.title = rss.title_
            /*mainActivity.setSupportActionBar(it)
            mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
            mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)*/
        }
    }
}

